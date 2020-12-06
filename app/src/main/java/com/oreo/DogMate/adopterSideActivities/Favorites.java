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
import com.oreo.DogMate.ListsAdapters.AdvertiserAdapter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Adopter;
import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * This Activity contains all the favorites bakers that the user have chosen.
 *
 */
public class Favorites extends Adopter_Navigation {
    private FirebaseAuth FireLog;// fire base authentication - will give us the user ID
    ListView listViewBakers; // The list view that will show the bakers
    String userID;
    DatabaseReference AdopterRef; //A reference to the adopter's favorites arrayList in the DB
    FirebaseDatabase DB; // A reference to our DataBase
    FirebaseUser currentUser; //A reference to the current user
    List<Advertiser> advertiserList; // the list that will contain the bakers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        currentUser = FireLog.getCurrentUser();
        userID = currentUser.getUid();
        listViewBakers = findViewById(R.id.bakers_list);
        advertiserList = new ArrayList<Advertiser>();
        AdopterRef = DB.getReference("Cutsomers").child(userID).child("favorites");

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        AdopterRef = DB.getReference("Users/Adopter").child(userID);
        /**
         * Adding the favorites bakers to the advertiserList.
         */
        AdopterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                advertiserList.clear();
                Adopter me =  dataSnapshot.getValue(Adopter.class);
                    for (Advertiser advertiser : me.getFavorites()) {
                        advertiserList.add(advertiser);
                    }

                if (advertiserList.isEmpty()) {
                    Toast.makeText(Favorites.this, "אין מועדפים", Toast.LENGTH_LONG).show();
                    return;
                }
                AdvertiserAdapter advertiserAdapter = new AdvertiserAdapter(Favorites.this, advertiserList);
                listViewBakers.setAdapter(advertiserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /**
         * when the adopter will press one advertiser,
         * the activity of the dogs of this advertiser will be opened
         */
        listViewBakers.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Favorites.this, AdopterMenuActivity.class);
                intent.putExtra("advertiser", advertiserList.get(i));
                startActivity(intent);
            }
        });
    }
}