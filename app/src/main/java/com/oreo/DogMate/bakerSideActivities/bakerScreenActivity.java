package com.oreo.DogMate.bakerSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.oreo.DogMate.Navigation.Baker_Navigation;
import com.oreo.DogMate.Navigation.Login;
import com.oreo.DogMate.R;

/**
 * The bakers main menu - the first screen he sees when he logs in
 */
public class bakerScreenActivity extends Baker_Navigation {

    Button myMenu;
    Button settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baker_screen);
        myMenu = (Button) findViewById(R.id.myMenu);
        settings = (Button) findViewById(R.id.settings);
        myMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToBakerMenu();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSettings();
            }
        });


    }

    private void moveToBakerMenu() {
        Intent intent = new Intent(this, BakerMenuActivity.class);
        startActivity(intent);
    }

    public void moveToBakerOrders(View v){
        startActivity(new Intent(getApplicationContext(), BakerOrderActivity.class));
    }
    public void moveToSettings(){
        startActivity(new Intent(getApplicationContext(), BakerSettingsActivity.class));
    }
    public void LogOutB (View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
