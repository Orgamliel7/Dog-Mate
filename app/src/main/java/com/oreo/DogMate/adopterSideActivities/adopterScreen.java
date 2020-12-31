package com.oreo.DogMate.adopterSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Navigation.Login;
import com.oreo.DogMate.R;

import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Main screen for the adopter - the first screen he sees when he logs in
 */
public class adopterScreen extends Adopter_Navigation {
    Button myOrders;
    Button newOrder;
    Button myFavorites;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_screen);

        myFavorites = findViewById(R.id.myFavorites);
        myOrders = findViewById(R.id.myOrders);
        newOrder =findViewById(R.id.newOrder);

    }

    public void LogOutC (View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void moveToSearch(View view) {
        Intent intent = new Intent(adopterScreen.this, SearchMainMenu.class);
        startActivity(intent);
    }

    public void moveToadopterOrders(View view) {
        Intent intent = new Intent(this, AdopterRequestActivity.class);
        startActivity(intent);
    }

    public void moveToFavorites(View view) {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }

}
