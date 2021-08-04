package com.example.myfirstapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.Date;

    /*
     * This class definition (or function) is derived
     * from the code available at the following location:
     *     https://www.youtube.com/watch?v=_nIoEAC3kLg&list=PLYx38U7gxBf32GGSGK-D6RDXEpmtgP5Al&index=7
     * The file has the following copyright from the original author:
     *     Technical Skillz
     */

public class SearchActivity extends AppCompatActivity {
    EditText inputSearch;
    RecyclerView recyclerView;

    FirebaseRecyclerOptions<Posts> options;
    FirebaseRecyclerAdapter<Posts, MyViewHolder> adapter;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search2);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        inputSearch = findViewById(R.id.ET_search);
        recyclerView = findViewById(R.id.result_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        LoadData("");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    LoadData(s.toString());  //user has input some search, which calls LoadData and filters out the posts
                } else {
                    LoadData("");  //means nothing has been typed
                }
            }
        });
    }


    //Loads the data from the database
    private void LoadData(String data) {
        Query query = dataRef.orderByChild("title").startAt(data).endAt(data+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts.class).build();
        adapter = new FirebaseRecyclerAdapter<Posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Posts model) {
                String PostID = getRef(position).getKey();
                holder.postid.setText(model.getTitle());
                holder.desc.setText(model.getDescription());
                holder.price.setText(model.getPrice());
                holder.time.setText(model.getDate());
                Picasso.get().load(model.getImage()).into(holder.imageView);   //get the image to the holder

                holder.v. setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, ActivityPostView.class);
                        intent.putExtra("PostID", PostID);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent,false);  //in their case is single_view, check this if not working
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
