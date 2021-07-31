package com.example.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

            //THIS FILE IS NOT USED AT THE MOMENT!!! KEPT JUST IN CASE









//public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
//
//    ArrayList<Posts> list;
//    public AdapterClass(ArrayList<Posts> list){
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AdapterClass.MyViewHolder holder, int position) {
//        holder.id.setText(list.get(position).getTitle());
//        holder.desc.setText(list.get(position).getDescription());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView id, desc;
////        ImageView img;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            id = itemView.findViewById(R.id.postID);
//            desc = itemView.findViewById(R.id.description);
////            img = itemView.findViewById(R.id.post_image);
//        }
//    }
//}


//public class AdapterClass extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//
//    ArrayList<Posts> list;
//    int typeView;
//    public AdapterClass(ArrayList<Posts> list, int type){
//        this.list = list;
//        typeView = type;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == 0) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
//            return new ViewHolderOne(view);
//        }
//        else if (viewType == 1) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_double_post_display, parent, false);
//            return new ViewHolderTwo(view);
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder.getItemViewType() == 0) {
//            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
//            ((ViewHolderOne) holder).id.setText(list.get(position).getTitle());
//            ((ViewHolderOne) holder).desc.setText(list.get(position).getDescription());
//        }
//        else if (holder.getItemViewType() == 1) {
//            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
//            ((ViewHolderTwo) holder).post_title.setText(list.get(position).getTitle());
//            ((ViewHolderTwo) holder).price.setText(list.get(position).getPrice());
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 0;
//        }
//        else if (position == 1) {
//            return 1;
//        }
////        return super.getItemViewType(position);
//        return 2;
//    }
//
//    public class ViewHolderOne extends RecyclerView.ViewHolder {
//        TextView id, desc;
//        public ViewHolderOne(@NonNull View itemView) {
//            super(itemView);
//            id = itemView.findViewById(R.id.postID);
//            desc = itemView.findViewById(R.id.description);
//        }
//    }
//
//    public class ViewHolderTwo extends RecyclerView.ViewHolder {
//        TextView post_title, price;
//        public ViewHolderTwo(@NonNull View itemView) {
//            super(itemView);
//            post_title = itemView.findViewById(R.id.post_title);
//            price = itemView.findViewById(R.id.price_tag);
//        }
//    }
//}
//

