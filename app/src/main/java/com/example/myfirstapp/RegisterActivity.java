package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseMethods firebaseMethods;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String email, password;
    private Context mContext = RegisterActivity.this;
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;
    private Button registerButton;
    private static final String SFU_DOMAIN = "sfu.ca";

    /*
    Initializes the Register activity. By clicking the register button, the register function should execute.
    @param NULL
     */
    private void init(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                if(checkInputs(email,password)){
                    mProgressBar.setVisibility(View.VISIBLE);

                    firebaseMethods.registerNewEmail(email,password);
                }
            }
        });
    }

    /*
    Checks if the email and password that is inputted is valid or not.
    @param email
    @param password
     */
    private boolean checkInputs(String email, String password){
        String domain = email.substring(email.indexOf("@")+1);
        if(email.equals("") || password.equals("")){
            toastMessage("Fields left empty.");
            return false;
        }
        else if(!domain.equals(SFU_DOMAIN)){
            toastMessage("Must be a valid @sfu.ca email.");
        }
        return true;
    }

    /*
    Initializes the widgets seen inside of the RegisterActivity Screen.
    @param NULL
     */
    private void initWidgets(){
        mEmail = (EditText) findViewById(R.id.editTextEmailRegister);
        mPassword = (EditText) findViewById(R.id.editTextPasswordRegister);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarRegister);
        registerButton = (Button) findViewById(R.id.registerButton);

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseMethods = new FirebaseMethods(mContext);
        setContentView(R.layout.activity_reg);
        initWidgets();
        setupFirebaseAuth();
        init();
    }

    /*
    Checks if a String is Null.
    @param string
     */
    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    /*
    A firebase listener that lets the user know if they are logged in when a auth state changes.
    @param NULL
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
    /*
    Creates a toast message.
    @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        //updateUI(currentUser);
    }

    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
