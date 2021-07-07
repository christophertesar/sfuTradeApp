package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;

    private FirebaseAuth mAuth;

    /**
     * Setup of Firebase authenticator
     */
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void checkCurrentUser(FirebaseUser user){
        if(user == null){
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);

        }
    }

    /**
    --------------------------------------------------------------------------------------------
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFirebaseAuth();

    }
}
