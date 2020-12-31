package com.oreo.DogMate.advertiserSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Navigation.Login;
import com.oreo.DogMate.R;

/**
 * The Advertisers main menu - the first screen he sees when he logs in
 */
public class advertiserScreenActivity extends Advertiser_Navigation {

    Button myMenu;
    Button settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_screen);
        myMenu = (Button) findViewById(R.id.myMenu);
        settings = (Button) findViewById(R.id.settings);
        myMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAdvertiserMenu();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSettings();
            }
        });


    }

    private void moveToAdvertiserMenu() {
        Intent intent = new Intent(this, advertiserMenuActivity.class);
        startActivity(intent);
    }

    public void moveToAdvertiserOrders(View v){
        startActivity(new Intent(getApplicationContext(), advertiserRequestActivity.class));
    }
    public void moveToSettings(){
        startActivity(new Intent(getApplicationContext(), advertiserSettingsActivity.class));
    }
    public void LogOutB (View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void addNewDog(View view) {
        Intent intent = new Intent(advertiserScreenActivity.this, AddDogActivity.class);
        startActivity(intent);
    }


}
