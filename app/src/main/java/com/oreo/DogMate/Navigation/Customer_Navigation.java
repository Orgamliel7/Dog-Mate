package com.oreo.DogMate.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.oreo.DogMate.R;
import com.oreo.DogMate.customerSideActivities.CustomerOrderActivity;
import com.oreo.DogMate.customerSideActivities.Favorites;
import com.oreo.DogMate.customerSideActivities.SearchMainMenu;
import com.oreo.DogMate.customerSideActivities.customerScreen;
import com.oreo.DogMate.customerSideActivities.customer_settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
/**
 * An activity that contains a side navigator for the customer,
 * all the customer's activities will inherit it
 */
public class Customer_Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Customer_Navigation(){ }

    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_customer_navigation, null);
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
            case R.id.customer_orders:
                Intent i = new Intent(this, CustomerOrderActivity.class);
                startActivity(i);
                break;

            case R.id.buy_pastry:
                Intent j = new Intent(this, SearchMainMenu.class);
                startActivity(j);
                break;

            case R.id.customer_favorites:
                Intent k = new Intent(this, Favorites.class);
                startActivity(k);
                break;

            case R.id.main_menu_customer:
                Intent l = new Intent(this, customerScreen.class);
                startActivity(l);
                break;

            case R.id.customer_settings:
                Intent m = new Intent(this, customer_settings.class);
                startActivity(m);
                break;


            case R.id.log_out_customer:
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
