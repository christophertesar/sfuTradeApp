package com.example.myfirstapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
//    public MyViewHolder(@NonNull View itemView) {
//        super(itemView);
//    }
    View v;
    TextView postid, desc, price;
    ImageView img;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        postid = itemView.findViewById(R.id.postID);
        desc = itemView.findViewById(R.id.description);
        price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.post_image);
        v = itemView;
    }

}
