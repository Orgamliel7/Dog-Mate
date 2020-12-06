package com.oreo.DogMate.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.oreo.DogMate.R;
import com.oreo.DogMate.adopterSideActivities.AdopterOrderActivity;
import com.oreo.DogMate.adopterSideActivities.Favorites;
import com.oreo.DogMate.adopterSideActivities.SearchMainMenu;
import com.oreo.DogMate.adopterSideActivities.adopterScreen;
import com.oreo.DogMate.adopterSideActivities.adopter_settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
/**
 * An activity that contains a side navigator for the adopter,
 * all the adopter's activities will inherit it
 */
public class Adopter_Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Adopter_Navigation(){ }

    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_adopter_navigation, null);
        FrameLayout container = (FrameLayout) drawer.findViewById(R.id.fragment_container2);
        getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("DogMate");

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.adopter_orders:
                Intent i = new Intent(this, AdopterOrderActivity.class);
                startActivity(i);
                break;

            case R.id.buy_dog:
                Intent j = new Intent(this, SearchMainMenu.class);
                startActivity(j);
                break;

            case R.id.adopter_favorites:
                Intent k = new Intent(this, Favorites.class);
                startActivity(k);
                break;

            case R.id.main_menu_adopter:
                Intent l = new Intent(this, adopterScreen.class);
                startActivity(l);
                break;

            case R.id.adopter_settings:
                Intent m = new Intent(this, adopter_settings.class);
                startActivity(m);
                break;


            case R.id.log_out_adopter:
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
