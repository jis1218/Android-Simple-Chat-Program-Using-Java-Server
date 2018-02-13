package com.insup.simplechat;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    Button button;
    RecyclerViewAdapter recyclerViewAdapter;
    Network network;
    ArrayList<String> list;
    Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListener();

    }

    private void init(){
        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        setHandler();
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        network = new Network(handler);
        list = new ArrayList<>();


        try {
            network.getMessageFromServer(recyclerViewAdapter, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.sendMessageToServer(editText.getText().toString());
                try {
                    network.getMessageFromServer(recyclerViewAdapter, list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("CountList", list.size() + "");
                recyclerViewAdapter.setNotifyRecyclerView(list);

            }
        });
    }

    private void setHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);

                switch (msg.what){
                    case 999 :
                        recyclerViewAdapter.setNotifyRecyclerView(list);
                        break;
                }
            }
        };
    }
}
