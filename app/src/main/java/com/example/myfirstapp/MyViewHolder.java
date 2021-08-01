package com.example.myfirstapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    View v;
    TextView postid, desc, price;
    ImageView imageView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        postid = itemView.findViewById(R.id.postID);
        desc = itemView.findViewById(R.id.description);
        price = itemView.findViewById(R.id.price);
        imageView = itemView.findViewById(R.id.post_image1);
        v = itemView;
    }

}
