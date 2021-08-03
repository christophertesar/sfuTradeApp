package com.example.myfirstapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//This is for DashboardFragment displaying the cardholder's information into the RecyclerView list
public class MyViewHolderThree extends RecyclerView.ViewHolder {
    View v;
    TextView postTitle, price, time;
    ImageView imageView;

    public MyViewHolderThree(@NonNull View itemView) {
        super(itemView);
        postTitle = itemView.findViewById(R.id.post_title);
        price = itemView.findViewById(R.id.price_tag);
        time = itemView.findViewById(R.id.date_time);
        imageView = itemView.findViewById(R.id.image_view2);
        v = itemView;
    }
}
