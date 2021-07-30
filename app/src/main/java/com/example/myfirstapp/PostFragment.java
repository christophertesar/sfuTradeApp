package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myfirstapp.UniversalImageLoader;


/*public class PostFragment extends Fragment {
    //Required empty public constructor
    public PostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.forum_post, container, false);

        //Will change the screen to the fill-in form screen of a create Want to Sell Post
//        Button btn_wts = v.findViewById(R.id.btn_WTS_post);
//        btn_wts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreatePostFragment createpostfragment = new CreatePostFragment();
//                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,createpostfragment).commit();
//            }
//        });

        //Leads to the fill-form for the making the WTS post
        /*Button btn_wts = v.findViewById(R.id.btn_WTS_post);
        btn_wts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreatePostActivity.class));
            }
        });

        return v;
    }
}*/

public class PostFragment extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener{

    private static final String TAG = "PostFragment";


    @Override
    public void getImagePath(Uri imagePath) {
        UniversalImageLoader.setImage(imagePath.toString(),mPostImage);

        mSelectedBitmap = null;
        mSelectedUri = imagePath;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        mPostImage.setImageBitmap(bitmap);

        mSelectedUri = null;
        mSelectedBitmap = bitmap;
    }

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCampus, mCell, mContactEmail, mOther;
    private Button mPost;
    private ProgressBar mProgressBar;

    //vars
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_post, container, false);
        mPostImage = view.findViewById(R.id.post_image);
        mTitle = view.findViewById(R.id.input_title);
        mDescription = view.findViewById(R.id.input_description);
        mPrice = view.findViewById(R.id.input_price);
        mCampus = view.findViewById(R.id.input_campus);
        mContactEmail = view.findViewById(R.id.input_email);
        mCell = view.findViewById(R.id.input_cell);
        mOther = view.findViewById(R.id.input_other);
        mPost = view.findViewById(R.id.btn_post);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return view;
    }

    private void init(){

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog to choose new photo");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getParentFragmentManager(), getString(R.string.dialog_select_photo));
                dialog.setTargetFragment(PostFragment.this, 1);
            }
        });
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


}
