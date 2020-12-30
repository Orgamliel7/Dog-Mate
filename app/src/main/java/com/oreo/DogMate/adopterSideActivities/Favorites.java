package com.oreo.DogMate.adopterSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.DogAdapter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Adopter;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * This Activity contains all the favorites Advertisers that the user have chosen.
 *
 */
public class Favorites extends Adopter_Navigation {
    private FirebaseAuth FireLog;// fire base authentication - will give us the user ID
    ListView listViewDog; // The list view that will show the Advertisers
    String userID;
    DatabaseReference AdopterRef; //A reference to the adopter's favorites arrayList in the DB
    FirebaseDatabase DB; // A reference to our DataBase
    FirebaseUser currentUser; //A reference to the current user
    List<Dog> dogList; // the list that will contain the Advertisers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        currentUser = FireLog.getCurrentUser();
        userID = currentUser.getUid();
        listViewDog = findViewById(R.id.advertisers_list);
        dogList = new ArrayList<Dog>();
        AdopterRef = DB.getReference("Adopter").child(userID).child("favorites");

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = Objects.requireNonNull(FireLog.getCurrentUser()).getUid();
        AdopterRef = DB.getReference("Users/Adopter").child(userID);
        /**
         * Adding the favorites Advertisers to the advertiserList.
         */
        AdopterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogList.clear();
                Adopter me =  dataSnapshot.getValue(Adopter.class);
                if (me != null) {
                    dogList.addAll(me.getFavorites());
                }

                if (dogList.isEmpty()) {
                    Toast.makeText(Favorites.this, "אין מועדפים", Toast.LENGTH_LONG).show();
                    return;
                }
                DogAdapter advertiserAdapter = new DogAdapter(Favorites.this, dogList);
                listViewDog.setAdapter(advertiserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /**
         * when the adopter will press one advertiser,
         * the activity of the dogs of this advertiser will be opened
         */
        listViewDog.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Favorites.this, dogWatchActivityAdopter.class);
                intent.putExtra("Dog", dogList.get(i));
                startActivity(intent);
            }
        });
    }
}