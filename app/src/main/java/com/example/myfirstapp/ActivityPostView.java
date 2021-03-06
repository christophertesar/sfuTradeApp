package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

//SearchActivity calls this when the user clicks on the certain post when using the search filter
public class ActivityPostView extends AppCompatActivity {
    ImageView imageView;
    TextView postTitle, price, description, sellerName, campus, mOther, email, phoneNumber, date;
    DatabaseReference ref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        postTitle = findViewById(R.id.post_view_post_title);
        price = findViewById(R.id.post_view_price);
        description = findViewById(R.id.post_view_description);
        sellerName = findViewById(R.id.post_view_seller_name);
        campus = findViewById(R.id.post_view_campus);
        mOther = findViewById(R.id.post_view_other);
        email = findViewById(R.id.post_view_email);
        phoneNumber = findViewById(R.id.post_view_phone_number);
        date = findViewById(R.id.post_view_date);
        imageView = findViewById(R.id.post_view_img);

        String PostID = getIntent().getExtras().get("PostID").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("title").getValue() != null) {
                    //Getting the values through the database with the PostID
                    String post_title = snapshot.child("title").getValue().toString();
                    String post_price = snapshot.child("price").getValue().toString();
                    String post_descp = snapshot.child("description").getValue().toString();
                    String post_seller_name = snapshot.child("name").getValue().toString();
                    String post_campus = snapshot.child("campus").getValue().toString();
                    String post_email = snapshot.child("email").getValue().toString();
                    String post_other = snapshot.child("other").getValue().toString();
                    String post_phoneNumber = snapshot.child("cell").getValue().toString();
                    String post_date = snapshot.child("date").getValue().toString();
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
                    date.setText(post_date);
                    Picasso.get().load(post_img).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
