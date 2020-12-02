package com.oreo.DogMate.advertiserSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oreo.DogMate.Objects.Address;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.R;

import java.util.HashMap;
import java.util.Map;

/**
 * the baker can change his password and personal details in here
 */
public class advertiserSettingsActivity extends Advertiser_Navigation {
    private FirebaseAuth FireLog = FirebaseAuth.getInstance();// fire base authentication
    String userID;
    FirebaseDatabase DB;
    DatabaseReference AdvertiserRef;
    FirebaseUser user;


    EditText oldPassword;
    EditText newPassword;
    EditText city;
    EditText street;
    EditText numOfHouse;
    EditText floor;
    EditText appartment;
    EditText phone;
    Button confirm;

    String newPasswordS,cityS, streetS, numOfHouseS, floorS,appartmentS, phoneS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advertiser_settings);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        numOfHouse = findViewById(R.id.house);
        floor = findViewById(R.id.floor);
        appartment = findViewById(R.id.appartment);
        phone = findViewById(R.id.phone);
        confirm = (Button)findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordS = newPassword.getText().toString().trim();
                cityS = city.getText().toString().trim();
                streetS = street.getText().toString().trim();
                numOfHouseS = numOfHouse.getText().toString().trim();
                floorS = floor.getText().toString().trim();
                appartmentS = appartment.getText().toString().trim();
                phoneS = phone.getText().toString().trim();

                Address address = new Address(cityS, streetS, numOfHouseS, floorS, appartmentS);
                updateBaker(cityS, streetS, numOfHouseS, floorS, appartmentS,phoneS,newPasswordS);

            }
        });

    }

    public void updateBaker(String cityS, String streetS,  String numOfHouseS, String floorS, String appartmentS,
                            final String phone, final String newPasswordS){
        user = FireLog.getCurrentUser();
        userID = FireLog.getCurrentUser().getUid();
        DB = FirebaseDatabase.getInstance();
        AdvertiserRef = DB.getReference("Users/Advertiser").child(userID);
        Map<String ,Object> updates= new HashMap<>();
        if(!(phone.isEmpty())) updates.put("phone", phone);
        if(!(cityS.isEmpty())) updates.put("address/city",cityS);
        if(!(streetS.isEmpty())) updates.put("address/streetName",streetS);
        if(!(numOfHouseS.isEmpty())) updates.put("address/buildingNumber",numOfHouseS);
        if(!(floorS.isEmpty())) updates.put("address/floor",floorS);
        if(!(appartmentS.isEmpty())) updates.put("address/appartmentNumber",appartmentS);

        AdvertiserRef.updateChildren(updates);
        if(!(newPasswordS.isEmpty())) user.updatePassword(newPasswordS);
        startActivity(new Intent(getApplicationContext(), advertiserScreenActivity.class));


    }

}
