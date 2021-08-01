package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class ActivityPostViewPersonal extends AppCompatActivity {
    Button btnDelete;
    ImageView imageView;
    TextView postTitle, price, description, sellerName;
    DatabaseReference ref, dataRef;
//    StorageReference storageRef;  //for img
    String userID, PostID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_personal);

        postTitle = findViewById(R.id.post_view_personal_post_title);
        price = findViewById(R.id.post_view_personal_price);
        description = findViewById(R.id.post_view_personal_description);
        sellerName = findViewById(R.id.post_view_personal_seller_name);
        btnDelete = findViewById(R.id.post_view_personal_delete_btn);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        PostID = getIntent().getExtras().get("PostID").toString();
        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userPosts").child(PostID);

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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCurrentPost();
            }
        });


    }

    private void DeleteCurrentPost() {
        dataRef = FirebaseDatabase.getInstance().getReference("Posts").child(PostID);
        dataRef.removeValue();
        dataRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userPosts").child(PostID);
        dataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ActivityPostViewPersonal.this, "You have successfully deleted the post!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityPostViewPersonal.this, MainApp.class);
                startActivity(intent);
            }
        });
    }
}
