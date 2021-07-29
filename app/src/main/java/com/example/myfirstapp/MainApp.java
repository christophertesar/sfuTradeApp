package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainApp extends AppCompatActivity {
    public Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        getSupportActionBar().setTitle("Dashboard");

//        openSearch();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            getSupportActionBar().setTitle("Dashboard");
                            break;

                        case R.id.nav_post:
                            selectedFragment = new PostFragment();
                            getSupportActionBar().setTitle("Post");
                            break;

                        case R.id.nav_market:
                            selectedFragment = new MarketFragment();
                            getSupportActionBar().setTitle("Marketplace");
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };


//    //shows the profile menu/icon
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.profile,menu);
//        return true;
//    }
//    //Links profile_pic to Account page.
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.profile)
//        {
//            Fragment accountfragment = new AccountFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,accountfragment).commit();
//            item.setVisible(false);
//            getSupportActionBar().setTitle("Account");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        return true;
//    }


    //shows the profile menu/icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }
    //Links profile_pic to Account page.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile)
        {
            Intent intent = new Intent(MainApp.this, AccountActivity.class);
            startActivity(intent);
//            item.setVisible(false);
//            getSupportActionBar().setTitle("Account");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }

    public void openSearch(){
        Button btn_search = (Button) findViewById(R.id.btn_search1);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainApp.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
