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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.widget.TextView;

import com.example.myfirstapp.UniversalImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CreatePostActivity extends AppCompatActivity implements SelectPhotoDialog.OnPhotoSelectedListener {
    private static final String TAG = "PostFragment";
    private static final int REQUEST_CODE = 2;

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCampus, mCell, mContactEmail, mOther;
    private Button mPost;
    private ProgressBar mProgressBar;

    private byte[] mUploadBytes;
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
                       //&& !isEmpty(mOther.getText().toString())
                       && !isEmpty(mCampus.getText().toString())){
                   if(mSelectedBitmap != null && mSelectedUri == null){
                        uploadNewPhoto(mSelectedBitmap);
                   }
                   else if(mSelectedUri != null && mSelectedBitmap == null){
                        uploadNewPhoto(mSelectedUri);
                   }
               }
               else{
                   Toast.makeText(getApplicationContext(),"Missing Fields.",Toast.LENGTH_SHORT).show();
               }
           }
        });
    }

    /**
     * uploads a new image based if a bitmap is the input.
     * @param bitmap
     */
    private void uploadNewPhoto(Bitmap bitmap){
        Log.d(TAG, "uploadNewPhoto: new photo bitmap to storage");
        BackgroundImageResize resize = new BackgroundImageResize(bitmap);
        Uri uri = null;
        resize.execute(uri);
    }

    /**
     * uploads a new image if a image path is the input.
     * @param imagePath
     */
    private void uploadNewPhoto(Uri imagePath){
        Log.d(TAG, "uploadNewPhoto: new photo from Uri to storage");
        BackgroundImageResize resize = new BackgroundImageResize(null);
        resize.execute(imagePath);
    }

    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {
        Bitmap mBitmap;

        /**
         * Setting the bitmap to resize.
         * @param bitmap
         */
        public BackgroundImageResize(Bitmap bitmap){
            if(bitmap != null){
                this.mBitmap = bitmap;
            }
        }

        /**
         * Before executing, the progress bar is shown.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        /**
         * Upload image and form information to Firebase.
         * @param bytes
         */
        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            mUploadBytes = bytes;
            hideProgressBar();

            executeUploadTask();
        }

        @Override
        protected byte[] doInBackground(Uri... params) {
            if(mBitmap == null){//no image
                try{
                    mBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),params[0]); //run in background because main app will be slow.
                }
                catch(IOException e){
                    Log.e(TAG, "IOException: doInBackgorund");
                }
            }
            byte[] bytes = null;
            bytes = getBytesFromBitmap(mBitmap, 100);
            return bytes;
        }
    }

    /**
     * Compiles data to create a form using the form and photo.
     */
    private void executeUploadTask(){
        final String postID = FirebaseDatabase.getInstance().getReference().push().getKey();
        Posts post = new Posts();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                post.setName(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("posts").child(postID);

        UploadTask uploadTask = storageReference.putBytes(mUploadBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Upload success!", Toast.LENGTH_SHORT).show();

                //download url storage
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                        post.setImage(uri.toString());
                        post.setEmail(mContactEmail.getText().toString());
                        post.setCampus(mCampus.getText().toString());
                        post.setTitle(mTitle.getText().toString());
                        post.setOther(mOther.getText().toString());
                        post.setDescription(mDescription.getText().toString());
                        post.setCell(mCell.getText().toString());
                        post.setPost_id(postID);
                        post.setPrice("$"+mPrice.getText().toString());
                        post.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(getString(R.string.node_posts))
                                .child(postID)
                                .setValue(post);
                        reference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userPosts").child(postID).setValue(post);
                        resetFields();
                        finish(); //Finish Current Activity
                        Intent intent = new Intent(CreatePostActivity.this, MainApp.class);
                        startActivity(intent);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to upload photo.", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }

    /**
     *
     * @param bitmap
     * @param quality
     * @return
     */
    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            return stream.toByteArray();
    }

    public void openDialog(){
        SelectPhotoDialog temp = new SelectPhotoDialog();
        temp.show(getSupportFragmentManager(),"SelectPhotoDialog");
        TextView insertPhotoTxt = findViewById(R.id.fp_insert_photo_txt);
        insertPhotoTxt.setVisibility(View.INVISIBLE);
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

