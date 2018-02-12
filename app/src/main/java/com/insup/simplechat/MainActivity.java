package com.insup.simplechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    Button button;
    RecyclerViewAdapter recyclerViewAdapter;
    Network network;
    ArrayList<String> list;



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
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        network = new Network();
        list = new ArrayList<>();
        network.getMessageFromServer(list);
    }

    private void setListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.sendMessageToServer(editText.getText().toString());
                network.getMessageFromServer(list);
                Log.d("CountList", list.size() + "");
                recyclerViewAdapter.setNotifyRecyclerView(list);

            }
        });
    }
}
