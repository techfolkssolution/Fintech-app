package com.example.lnp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lnp.R;
import com.example.lnp.fragment.ContactUs;
import com.example.lnp.fragment.Home;
import com.example.lnp.fragment.Ledger;
import com.example.lnp.fragment.Profile;
import com.example.lnp.fragment.Wallet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigation;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FindViewById
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigation = findViewById(R.id.bottomNavigation);


        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_nav,
                R.string.close_nav
        );
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home()).commit();
        }


        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottomProfile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Profile()).commit();
                    return true;
                } else if (itemId == R.id.bottomHome) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home()).commit();
                    return true;
                } else if (itemId == R.id.bottomLedger) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Ledger()).commit();
                    return true;
                } else if (itemId == R.id.bottomWallet) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Wallet()).commit();
                    return true;
                }
                return false;
            }
        });
        displayDialog();
    }


    public void displayDialog() {
        ImageView closeButton;
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.advertisement);
        closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.navContactUs) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ContactUs()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}