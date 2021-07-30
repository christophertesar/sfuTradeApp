package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MarketFragment extends Fragment {
//    EditText inputSearch;
//    RecyclerView recyclerView;
//
//    FirebaseRecyclerOptions<Posts> options;
//    FirebaseRecyclerAdapter<Posts, AdapterClass.MyViewHolder> adapter;
//    DatabaseReference DataRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_market, container, false);
        Button btn_search = v.findViewById(R.id.btn_search1);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
//        DataRef = FirebaseDatabase.getInstance().getReference().child("Posts");
//        inputSearch = v.findViewById(R.id.search_btn);
//        recyclerView = v.findViewById(R.id.result_view);
//        RecyclerView.la
//        LoadData();
        return v;
    }

//    private void LoadData() {
//        options new FirebaseRecyclerOptions<.Builder<>()>()
//    }
}

