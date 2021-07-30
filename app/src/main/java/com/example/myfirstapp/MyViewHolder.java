package com.example.myfirstapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
//    public MyViewHolder(@NonNull View itemView) {
//        super(itemView);
//    }
    TextView postid, desc;
//    ImageView img;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        postid = itemView.findViewById(R.id.postID);
        desc = itemView.findViewById(R.id.description);
//            img = itemView.findViewById(R.id.post_image);
    }

}
