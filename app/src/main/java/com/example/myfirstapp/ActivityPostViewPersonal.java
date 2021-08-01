package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        String post_title = getIntent().getStringExtra("postName");
        String post_price = getIntent().getStringExtra("postPrice");
        String post_descp = getIntent().getStringExtra("postDescp");
//        String post_sellerName = getIntent().getStringExtra("postName");
        postTitle.setText(post_title);
        price.setText(post_price);
        description.setText(post_descp);


        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataRef = FirebaseDatabase.getInstance().getReference("Users").child("userPosts");
        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userPosts");
//        Toast.makeText(ActivityPostViewPersonal.this,""+ref.getRef().orderByChild("title").toString(),Toast.LENGTH_LONG).show();

//        if (ref.orderByChild("title").toString().equals(post_title)) {
//            dataRef = ref.child("title").getParent();

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
//                        startActivity(new Intent(ActivityPostViewPersonal.this, MainApp.class));
                        }
                    });
                }
            });

        }

    }
//}
