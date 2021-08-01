package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ActivityPostView extends AppCompatActivity {
    ImageView imageView;
    TextView postTitle, price, description, sellerName;
    DatabaseReference ref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        postTitle = findViewById(R.id.post_view_post_title);
        price = findViewById(R.id.post_view_price);
        description = findViewById(R.id.post_view_description);
        sellerName = findViewById(R.id.post_view_seller_name);

        String PostID = getIntent().getExtras().get("PostID").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post_title = snapshot.child("title").getValue().toString();
                String post_price = snapshot.child("price").getValue().toString();
                String post_descp = snapshot.child("description").getValue().toString();
                postTitle.setText(post_title);
                price.setText(post_price);
                description.setText(post_descp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
