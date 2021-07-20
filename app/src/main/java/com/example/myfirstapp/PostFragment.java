package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class PostFragment extends Fragment {
    //Required empty public constructor
    public PostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_post, container, false);

        //Will change the screen to the fill-in form screen of a create Want to Sell Post
//        Button btn_wts = v.findViewById(R.id.btn_WTS_post);
//        btn_wts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreatePostFragment createpostfragment = new CreatePostFragment();
//                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,createpostfragment).commit();
//            }
//        });

        Button btn_wts = v.findViewById(R.id.btn_WTS_post);
        btn_wts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreatePostActivity.class));
            }
        });
        return v;
    }
}

