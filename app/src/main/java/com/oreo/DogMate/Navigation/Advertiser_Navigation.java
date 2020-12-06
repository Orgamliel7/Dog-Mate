package com.oreo.DogMate.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.oreo.DogMate.R;
import com.oreo.DogMate.advertiserSideActivities.advertiserMenuActivity;
import com.oreo.DogMate.advertiserSideActivities.advertiserOrderActivity;
import com.oreo.DogMate.advertiserSideActivities.advertiserSettingsActivity;
import com.oreo.DogMate.advertiserSideActivities.advertiserScreenActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * An activity that contains a side navigator for the advertiser
 * all the advertiser's activities will inherit it
 */
public class Advertiser_Navigation extends Activity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;

    public Advertiser_Navigation(){ }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_advertiser_navigation, null);
        FrameLayout container = (FrameLayout) drawer.findViewById(R.id.fragment_container);
        getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DogMate");

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.my_menu:
                Intent i = new Intent(this, advertiserMenuActivity.class);
                startActivity(i);
                break;

            case R.id.advertiser_orders:
                Intent j = new Intent(this, advertiserOrderActivity.class);
                startActivity(j);
                break;

            case R.id.main_menu:
                Intent k = new Intent(this, advertiserScreenActivity.class);
                startActivity(k);
                break;
            case R.id.advertiser_settings:
                Intent l = new Intent(this, advertiserSettingsActivity.class);
                startActivity(l);
                break;

            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    }

