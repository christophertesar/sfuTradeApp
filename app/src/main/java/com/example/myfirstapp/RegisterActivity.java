package com.example.myfirstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseMethods firebaseMethods;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myReference;
    private FirebaseUser user;
    private String userID;


    private String name, email, password;
    private Context mContext = RegisterActivity.this;
    private EditText mName, mEmail, mPassword;
    private ProgressBar mProgressBar;
    private Button registerButton;
    private static final String SFU_DOMAIN = "sfu.ca";

    /**
    Initializes the Register activity. By clicking the register button, the register function should execute.
     */
    private void init(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = mName.getText().toString();
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                mProgressBar.setVisibility(View.VISIBLE);
                if(checkInputs(email,password)){
                    firebaseMethods.registerNewEmail(name, email, password);
                    mProgressBar.setVisibility(View.GONE);
                    toastMessage("Successfully registered.");
                }
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
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
            return false;
        }
        return true;
    }

    /**
    Initializes the widgets seen inside of the RegisterActivity Screen.
     */
    private void initWidgets(){
        mName = (EditText) findViewById(R.id.editTextFullNameRegister);
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

    /**
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

    /**
    A firebase listener that lets the user know if they are logged in when a auth state changes.
     */
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myReference = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    //toastMessage("Successfully signed in with:" + user.getEmail());
                    myReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mAuth.signOut();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    finish();
                }
                else {
                    //toastMessage("Successfully signed out.");
                }
            }
        };
    }
    /**
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
    }

    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
