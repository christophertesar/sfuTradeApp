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
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.myfirstapp.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class CreatePostActivity extends AppCompatActivity implements SelectPhotoDialog.OnPhotoSelectedListener {
    private static final String TAG = "PostFragment";
    private static final int REQUEST_CODE = 2;

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCampus, mCell, mContactEmail, mOther;
    private Button mPost;
    private ProgressBar mProgressBar;

    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_post);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(CreatePostActivity.this));

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
        mPost.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Log.d(TAG, "onClick: attempting to post...");
               if(!isEmpty(mTitle.getText().toString())
                       && !isEmpty(mDescription.getText().toString())
                       && !isEmpty(mContactEmail.getText().toString())
                       && !isEmpty(mPrice.getText().toString())
                       && !isEmpty(mCell.getText().toString())
                       && !isEmpty(mOther.getText().toString())
                       && !isEmpty(mCampus.getText().toString())){
                   if(mSelectedBitmap != null && mSelectedUri == null){
                        uploadNewPhoto(mSelectedBitmap);
                   }
                   else if(mSelectedUri != null && mSelectedBitmap == null){
                        uploadNewPhoto(mSelectedUri);
                   }
               }
           }
        });
    }

    private void uploadNewPhoto(Bitmap bitmap){

    }

    private void uploadNewPhoto(Uri imagePath){

    }

    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {
        Bitmap bitmap;

        public BackgroundImageResize(Bitmap bitmap){
            if(bitmap != null){
                this.bitmap = bitmap;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
        }

        @Override
        protected byte[] doInBackground(Uri... uris) {
            return new byte[0];
        }
    }

    public void openDialog(){
        SelectPhotoDialog temp = new SelectPhotoDialog();
        temp.show(getSupportFragmentManager(),"SelectPhotoDialog");
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

    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        UniversalImageLoader.setImage(imagePath.toString(), mPostImage);
        //assign to global variable
        mSelectedBitmap = null;
        mSelectedUri = imagePath;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        mPostImage.setImageBitmap(bitmap);
        //assign to a global variable
        mSelectedUri = null;
        mSelectedBitmap = bitmap;
    }

}

