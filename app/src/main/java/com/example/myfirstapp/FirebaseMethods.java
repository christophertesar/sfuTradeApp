package com.example.myfirstapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMethods {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;

    private Context mContext;


    /**
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

    /**
    Adds a new email and password to the firebase database
    @param email
    @param password
     */
    public void registerNewEmail(final String name, final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(mContext, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                    else if(task.isSuccessful()){
                        sendVerificationEmail();
//                        userID = mAuth.getCurrentUser().getUid();
                        saveUserRegisterData(name, email);
                    }
                }
            });
    }


    //This function sends a verification email to the newly created account to verify.
    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }
                            else{
                                Toast.makeText(mContext, "Verification email could not be sent.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //save user's registered data into the database based on their uid as the key
    private void saveUserRegisterData (final String name, final String email){
//        User person = new User(name, email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myReference = db.getReference("Users");

        User person = new User();
        person.setName(name);
        person.setEmail(email);

//        Map<String, String> userMap = new HashMap<>();
//        userMap.put("name", name);
//        userMap.put("email", email);
        myReference.child(userID).setValue(person);

        //this part im not sure if i need it to retrieve it back from database
    }

}
