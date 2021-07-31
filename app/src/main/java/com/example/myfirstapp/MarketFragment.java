package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class MarketFragment extends Fragment {
//    EditText inputSearch;
//    RecyclerView recyclerView;
//
//    FirebaseRecyclerOptions<Posts> options;
//    FirebaseRecyclerAdapter<Posts, AdapterClass.MyViewHolder> adapter;
//    DatabaseReference DataRef;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    FirebaseRecyclerOptions<Posts> options;
    FirebaseRecyclerAdapter<Posts, MyViewHolderTwo> adapter2;
    DatabaseReference dataRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_market, container, false);
        Button btn_search = v.findViewById(R.id.btn_search1);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });


//        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.result_view);
//        AdapterClass adapterClass = new AdapterClass();
//        recyclerView.setAdapter(adapterClass);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);

//        ArrayList<Posts> list;
        dataRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        recyclerView = v.findViewById(R.id.result_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        LoadData();
        return v;
    }

    private void LoadData() {
//        Query query = dataRef.orderByChild("title").startAt(data).endAt(data+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(dataRef, Posts.class).build();
        adapter2 = new FirebaseRecyclerAdapter<Posts, MyViewHolderTwo>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderTwo holder, int position, @NonNull Posts model) {
//                final String PostName = dataRef.getParent;

                final String postTitle = model.getTitle();
                final String postPrice = model.getPrice();
                final String postDescp = model.getDescription();
//                final String postSellerName = model

                holder.postTitle.setText(model.getTitle());
                holder.price.setText(model.getPrice());
//                Picasso.get().load(model.getImageUrl()).into(holder.img);   //get the image to the holder
                holder.v. setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityPostView.class);
//                        intent.putExtra("PostID", getRef().getKey());
                        intent.putExtra("postName",""+postTitle);
                        intent.putExtra("postPrice",""+postPrice);
                        intent.putExtra("postDescp",""+postDescp);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolderTwo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_double_post_display, parent,false);  //in their case is single_view, check this if not working
                return new MyViewHolderTwo(v);
            }
        };
        adapter2.startListening();
        recyclerView.setAdapter(adapter2);
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(this, ActivityPostView.class);
//        startActivity(intent);
//    }
}

