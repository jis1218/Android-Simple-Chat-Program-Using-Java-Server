package com.insup.simplechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2018-02-12.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {
    ArrayList<String> list;
    Context context;

    public RecyclerViewAdapter(Context context) {
       this.context = context;
    }

    public void setNotifyRecyclerView(ArrayList<String> list){
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.textChat.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textChat;
        public MyHolder(View itemView) {
            super(itemView);
            textChat = itemView.findViewById(R.id.textChat);
        }
    }
}
