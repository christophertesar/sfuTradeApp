package com.example.myfirstapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Queue;

public class SearchActivity extends AppCompatActivity {
//    DatabaseReference databaseReference;
//    ArrayList<Posts> list;
//    RecyclerView recyclerView;
//    SearchView searchView;


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
                    LoadData(s.toString());
                }
                else {
                    LoadData("");  //means nothing has been typed
                }
            }
        });
        //next step is a btn listener where it user will open up the post into a PostViewActivity




//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
//        recyclerView = findViewById(R.id.result_view);
//        searchView = findViewById(R.id.search_view);
    }

    private void LoadData(String data) {
        Query query = dataRef.orderByChild("title").startAt(data).endAt(data+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts.class).build();
        adapter = new FirebaseRecyclerAdapter<Posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Posts model) {
                holder.postid.setText(model.getTitle());
                holder.desc.setText(model.getDescription());
//                Picasso.get().load(model.getImageUrl()).into(holder.img);   //get the image to the holder
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
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (databaseReference != null){
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists())
//                    {
//                        for (DataSnapshot ds: snapshot.getChildren())
//                        {
//                            list.add(ds.getValue(Posts.class));
//                        }
//                        AdapterClass adapterClass = new AdapterClass(list);
//                        recyclerView.setAdapter(adapterClass);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(SearchActivity.this, "SearchActivity error!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
}
