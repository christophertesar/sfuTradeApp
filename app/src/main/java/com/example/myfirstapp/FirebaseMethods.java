package com.example.myfirstapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseMethods {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;

    private Context mContext;


    /*
    Get instance of firebase authenticator based on context. Retrieves user ID if user is logged in.
    @param context
     */
    public FirebaseMethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();

        }
    }

    /*
    Adds a new email and password to the firebase database
    @param email
    @param password
     */
    public void registerNewEmail(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(mContext, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                    else if(task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                    }
                }
            });
    }
}
