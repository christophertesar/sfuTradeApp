package com.example.myfirstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//creates an activity for the Post fill-form. The [Post] Want to Sell Button calls to this activity.
public class CreatePostFragment extends Fragment {
    public CreatePostFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        View v = inflater.inflate(R.layout.fragment_post_createpost, container, false);

        return v;
    }


}
