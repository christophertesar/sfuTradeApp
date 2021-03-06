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

//Main app screen activities
public class MainApp extends AppCompatActivity {
    public Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //setting up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //executing the bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        getSupportActionBar().setTitle("Dashboard");

    }

    // for bottom navigation of the dashboard, post, and marketplace
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            getSupportActionBar().setTitle("Dashboard");
                            getSupportActionBar().show();
                            break;

                        case R.id.nav_post:
                            Intent intent = new Intent(MainApp.this, CreatePostActivity.class);
                            startActivity(intent);
                            return false;

                        case R.id.nav_market:
                            selectedFragment = new MarketFragment();
                            getSupportActionBar().setTitle("Marketplace");
                            getSupportActionBar().show();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

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
        }
        return true;
    }

}
