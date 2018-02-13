package com.insup.simplechat;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by 정인섭 on 2018-02-12.
 */

public class Network {
    private static final int PORT_NUM = 9090;
    Socket clientSocket = null;
    OutputStream outputStream = null;
    BufferedReader bufferedReader = null;
    Handler handler;
    public Network(Handler handler) {
        this.handler = handler;
        runNetwork();
    }

    public void runNetwork() {

        Log.d("Network", "Network 연결됨");
        new Thread() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket("192.168.0.9", PORT_NUM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendMessageToServer(final String msg) {
        new Thread(){
            @Override
            public void run() {
                if (clientSocket != null) {
                    String finalMSG = msg + "\r\n";
                    try {
                        outputStream = clientSocket.getOutputStream();
                        outputStream.write(finalMSG.getBytes());
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void getMessageFromServer(final RecyclerViewAdapter recyclerViewAdapter, final ArrayList<String> list) throws IOException {

        new Thread(){
            @Override
            public void run() {
                try{
                    while(true){

                        if(clientSocket!=null){
                            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            list.add(bufferedReader.readLine());
                            handler.sendEmptyMessage(999);
                        }
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
