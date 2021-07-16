package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private Context mContext = MainActivity.this;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //UI
    private EditText mEmail, mPassword;
    private Button btnSignIn, btnSignOut;
    private ProgressBar mProgressBar;

    /**
     * Setup of Firebase authenticator
     */
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    toastMessage("Successfully signed in with:" + user.getEmail());
                }
                else {
                    toastMessage("Successfully signed out.");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
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

        mEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mPassword = (EditText) findViewById(R.id.editTextTextPassword);
        btnSignIn = (Button) findViewById(R.id.button);
        btnSignOut = (Button) findViewById(R.id.button2);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        setupFirebaseAuth();
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if(!email.equals("") && !pass.equals("")){
                    mProgressBar.setVisibility((View.VISIBLE)); //The progress bar shows up.
                    mAuth.signInWithEmailAndPassword(email,pass) //Sign into firebase using email and password.
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    mProgressBar.setVisibility(View.GONE);
                                }
                                else{
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                }
                else{
                    toastMessage("Fields not filled.");
                }

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mAuth.signOut();
                toastMessage("Signing out.");
            }
        });

    }
}


