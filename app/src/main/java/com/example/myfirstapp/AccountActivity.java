package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);

        toolbarSetup();
        displayUserData();
        signOut();
    }


    //set up the toolbar
    private void toolbarSetup(){
        //set up the Account's toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //if user sign out, sign out user from firebase and goes back to main page
    private void signOut() {
        Button logout = (Button) findViewById(R.id.btn_signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(AccountActivity.this, "You have succesfully sign out", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Displays the user's details in the page
    private void displayUserData() {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        final TextView emailTextView = (TextView) findViewById(R.id.email_address);
        final TextView nameTextView = (TextView) findViewById(R.id.FullName);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                nameTextView.setText(name);
                emailTextView.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

}