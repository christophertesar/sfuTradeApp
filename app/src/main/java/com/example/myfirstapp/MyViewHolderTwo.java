package com.example.myfirstapp;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderTwo extends RecyclerView.ViewHolder{

    View v;
    TextView postTitle, price;
    ImageView img;
    public MyViewHolderTwo(@NonNull View itemView) {
        super(itemView);
        postTitle = itemView.findViewById(R.id.post_title);
        price = itemView.findViewById(R.id.price_tag);
        img = itemView.findViewById(R.id.post_image);
        v = itemView;
    }

}
