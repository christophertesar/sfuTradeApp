package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

//the postview is specifically for viewing user's personal posts, has the additional function of Delete button that will delete the posts from the database
public class ActivityPostViewPersonal extends AppCompatActivity {
    Button btnDelete;
    ImageView imageView;
    TextView postTitle, price, description, sellerName, campus, mOther, email, phoneNumber;
    DatabaseReference ref, dataRef, nameRef;
    String userID, PostID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_personal);
        toolbarSetup();

        postTitle = findViewById(R.id.post_view_personal_post_title);
        price = findViewById(R.id.post_view_personal_price);
        description = findViewById(R.id.post_view_personal_description);
        sellerName = findViewById(R.id.post_view_personal_seller_name);
        campus = findViewById(R.id.post_view_personal_campus);
        mOther = findViewById(R.id.post_view_personal_other);
        email = findViewById(R.id.post_view_personal_email);
        phoneNumber = findViewById(R.id.post_view_personal_phone_number);
        imageView = findViewById(R.id.post_view_personal_img);

        btnDelete = findViewById(R.id.post_view_personal_delete_btn);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        PostID = getIntent().getExtras().get("PostID").toString();
        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userPosts").child(PostID);
        nameRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("name");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("title").getValue() != null) {
                    //Getting the values through the database with the PostID
                    String post_title = snapshot.child("title").getValue().toString();
                    String post_price = snapshot.child("price").getValue().toString();
                    String post_descp = snapshot.child("description").getValue().toString();
                    String post_campus = snapshot.child("campus").getValue().toString();
                    String post_email = snapshot.child("email").getValue().toString();
                    String post_other = snapshot.child("other").getValue().toString();
                    String post_phoneNumber = snapshot.child("cell").getValue().toString();
                    String post_seller_name = snapshot.child("name").getValue().toString();
                    String post_img = snapshot.child("image").getValue().toString();

                    //sets the text boxes with the appropriate values
                    postTitle.setText(post_title);
                    price.setText(post_price);
                    description.setText(post_descp);
                    campus.setText(post_campus);
                    email.setText(post_email);
                    mOther.setText(post_other);
                    sellerName.setText(post_seller_name);
                    phoneNumber.setText(post_phoneNumber);

                    Picasso.get().load(post_img).into(imageView);
                }
                else{
                    Log.d("ActivityPostPersonal", "Deleting post; skipped listener");
                }
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

    //deletes the post in the "Posts" and the "Users" personal "userPosts" path
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

    //set up the toolbar
    private void toolbarSetup(){
        //set up the Account's toolbar
        Toolbar toolbar = findViewById(R.id.post_view_personal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
