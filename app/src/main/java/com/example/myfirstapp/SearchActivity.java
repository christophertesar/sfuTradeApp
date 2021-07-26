package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<Posts> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search2);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
        recyclerView = findViewById(R.id.result_view);
        searchView = findViewById(R.id.search_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (databaseReference != null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            list.add(ds.getValue(Posts.class));
                        }
                        AdapterClass adapterClass = new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, "SearchActivity error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
