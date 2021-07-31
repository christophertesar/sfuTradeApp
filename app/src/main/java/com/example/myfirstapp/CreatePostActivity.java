//package com.example.myfirstapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.content.Context;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import com.example.myfirstapp.UniversalImageLoader;
//
//import androidx.appcompat.app.AppCompatActivity;


//public class CreatePostActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.forum_post);
//    }
//
//}
//

package com.example.myfirstapp;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class CreatePostActivity extends AppCompatActivity {
    private static final String TAG = "PostFragment";
    private static final int REQUEST_CODE = 2;

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCampus, mCell, mContactEmail, mOther;
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
        mCampus = findViewById(R.id.input_campus);
        mContactEmail = findViewById(R.id.input_email);
        mCell = findViewById(R.id.input_cell);
        mOther = findViewById(R.id.input_other);
        mPost = findViewById(R.id.btn_post);
        mProgressBar = findViewById(R.id.progressBar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();
        verifyPermissions();
    }
    private void init(){

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog to choose new photo");
                openDialog();
            }
        });
    }

    public void openDialog(){
        SelectPhotoDialog temp = new SelectPhotoDialog();
        temp.show(getSupportFragmentManager(),"Test");
    }
    private void resetFields(){
        UniversalImageLoader.setImage("", mPostImage);
        mTitle.setText("");
        mDescription.setText("");
        mPrice.setText("");
        mCampus.setText("");
        mCell.setText("");
        mContactEmail.setText("");
        mOther.setText("");
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

    private void verifyPermissions(){
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(CreatePostActivity.this,
                    permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        verifyPermissions();
    }

}

