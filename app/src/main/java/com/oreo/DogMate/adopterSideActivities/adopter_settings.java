package com.oreo.DogMate.adopterSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Address;
import com.oreo.DogMate.R;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity in which the adopter can change his password and details
 */
public class adopter_settings extends Adopter_Navigation {

    private FirebaseAuth FireLog = FirebaseAuth.getInstance();// fire base authentication
    String userID;
    FirebaseDatabase DB;
    DatabaseReference advertiserRef;
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
        setContentView(R.layout.activity_adopter_settings);
        oldPassword = findViewById(R.id.oldPasswordC);
        newPassword = findViewById(R.id.newPasswordC);
        city = findViewById(R.id.cityC);
        street = findViewById(R.id.streetC);
        numOfHouse = findViewById(R.id.houseC);
        floor = findViewById(R.id.floorC);
        appartment = findViewById(R.id.appartmentC);
        phone = findViewById(R.id.phoneC);
        confirm = (Button)findViewById(R.id.confirmC);

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
                updateCustomer(cityS, streetS, numOfHouseS, floorS, appartmentS,phoneS,newPasswordS);

            }
        });
    }
    public void updateCustomer(String cityS, String streetS,  String numOfHouseS, String floorS, String appartmentS,
                            final String phone, final String newPasswordS){
        user = FireLog.getCurrentUser();
        userID = FireLog.getCurrentUser().getUid();
        DB = FirebaseDatabase.getInstance();
        advertiserRef = DB.getReference("Users/Adopter").child(userID);
        Map<String ,Object> updates= new HashMap<>();
        if(!(phone.isEmpty())) updates.put("phone", phone);
        if(!(cityS.isEmpty())) updates.put("address/city",cityS);
        if(!(streetS.isEmpty())) updates.put("address/streetName",streetS);
        if(!(numOfHouseS.isEmpty())) updates.put("address/buildingNumber",numOfHouseS);
        if(!(floorS.isEmpty())) updates.put("address/floor",floorS);
        if(!(appartmentS.isEmpty())) updates.put("address/appartmentNumber",appartmentS);

        advertiserRef.updateChildren(updates);
        if(!(newPasswordS.isEmpty())) user.updatePassword(newPasswordS);
        startActivity(new Intent(getApplicationContext(), adopterScreen.class));


    }
}
