package com.example.myfirstapp;


import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Queue;

//Victor's implementation
//public class SearchActivity extends AppCompatActivity {
//    DatabaseReference databaseReference;
//    ArrayList<Posts> list;
//    RecyclerView recyclerView;
//    SearchView searchView;



//import android.*;
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import android.net.Uri;
//import android.os.Bundle;
//
//import com.google.android.material.tabs.TabLayout;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.viewpager.widget.ViewPager;
//import androidx.appcompat.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.SectionIndexer;
//import android.widget.TableLayout;
//
//
//import com.example.myfirstapp.SectionsPagerAdapter;


public class SearchActivity extends AppCompatActivity {

//    private static final String TAG = "SearchActivity";
//    private static final int REQUEST_CODE = 1;
//
//    //widgets
//    private TabLayout mTabLayout;
//    public ViewPager mViewPager;
//
//    //vars
//    public SectionsPagerAdapter mPagerAdapter;

    EditText inputSearch;
    RecyclerView recyclerView;

    FirebaseRecyclerOptions<Posts> options;
    FirebaseRecyclerAdapter<Posts, MyViewHolder> adapter;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search2);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        inputSearch = findViewById(R.id.ET_search);
        recyclerView = findViewById(R.id.result_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        LoadData("");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    LoadData(s.toString());
                } else {
                    LoadData("");  //means nothing has been typed
                }
            }
        });
    }


    private void LoadData(String data) {
        Query query = dataRef.orderByChild("title").startAt(data).endAt(data+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts.class).build();
        adapter = new FirebaseRecyclerAdapter<Posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Posts model) {

                String PostID = getRef(position).getKey();
                holder.postid.setText(model.getTitle());
                holder.desc.setText(model.getDescription());
                holder.price.setText(model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView);   //get the image to the holder

                holder.v. setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, ActivityPostView.class);
                        intent.putExtra("PostID", PostID);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent,false);  //in their case is single_view, check this if not working
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


    //Victor implementation
//        setContentView(R.layout.activity_search);
//        mTabLayout = (TabLayout) findViewById(R.id.tabs);
//        mViewPager  = (ViewPager) findViewById(R.id.viewpager_container);
//
//        verifyPermissions();
//    }
//
//    private void setupViewPager(){
//        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        //mPagerAdapter.addFragment(new SearchFragment());
//        //mPagerAdapter.addFragment(new WatchListFragment());
//        mPagerAdapter.addFragment(new PostFragment());
//        mPagerAdapter.addFragment(new AccountFragment());
//
//        mViewPager.setAdapter(mPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//        //mTabLayout.getTabAt(0).setText(getString(R.string.fragment_search));
//        //mTabLayout.getTabAt(1).setText(getString(R.string.fragment_watch_list));
//        //mTabLayout.getTabAt(2).setText(getString(R.string.fragment_post));
//        //mTabLayout.getTabAt(3).setText(getString(R.string.fragment_account));
//
//    }
//


//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        verifyPermissions();
//    }
}
