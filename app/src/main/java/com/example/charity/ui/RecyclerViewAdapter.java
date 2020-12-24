package com.example.charity.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charity.R;
import com.example.charity.model.User;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> userList;

    public RecyclerViewAdapter(Context context, ArrayList<User> userList) {
        super();
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.username.setText(userList.get(position).getUsername());
        holder.address.setText(userList.get(position).getAddress());
        holder.mobile.setText(userList.get(position).getPhone());
        holder.need.setText(userList.get(position).getNeedsOrGift());
        holder.time.setText(userList.get(position).getReadableDate());

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username, address, need, mobile, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.fullname);
            address = itemView.findViewById(R.id.address);
            need = itemView.findViewById(R.id.needs);
            mobile = itemView.findViewById(R.id.mobile);
            time = itemView.findViewById(R.id.time);

        }


    }
}
