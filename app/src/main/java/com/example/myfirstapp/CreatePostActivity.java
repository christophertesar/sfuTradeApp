package com.example.myfirstapp;

import android.content.Intent;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.myfirstapp.UniversalImageLoader;
import androidx.appcompat.app.AppCompatActivity;


public class CreatePostActivity extends AppCompatActivity {
    private static final String TAG = "PostFragment";

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCountry, mStateProvince, mCity, mContactEmail;
    private Button mPost;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_post);

        mPostImage = findViewById(R.id.post_image);
        mTitle = findViewById(R.id.input_title);
        mDescription = findViewById(R.id.input_description);
        mPrice = findViewById(R.id.input_price);
        mCountry = findViewById(R.id.input_country);
        mStateProvince = findViewById(R.id.input_state_province);
        mCity = findViewById(R.id.input_city);
        mContactEmail = findViewById(R.id.input_email);
        mPost = findViewById(R.id.btn_post);
        mProgressBar = findViewById(R.id.progressBar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    private void init(){

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog to choose new photo");

            }
        });
    }

    private void resetFields(){
        UniversalImageLoader.setImage("", mPostImage);
        mTitle.setText("");
        mDescription.setText("");
        mPrice.setText("");
        mCountry.setText("");
        mStateProvince.setText("");
        mCity.setText("");
        mContactEmail.setText("");
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    private boolean isEmpty(String string){
        return string.equals("");
    }

}
