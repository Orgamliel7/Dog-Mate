package com.oreo.DogMate.adopterSideActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.DogAdapter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.Objects.Preference;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

public class dogsResultsAdopter extends Adopter_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewdogs;
    String userID;
    DatabaseReference menuForadopter;
    FirebaseDatabase DB;
    List<Dog> dogList;
    TextView noResults;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_results_adopter);
        Intent intent = getIntent();
        preference = (Preference) intent.getSerializableExtra("Preference");
        noResults = findViewById(R.id.noResults2);
        noResults.setVisibility(View.INVISIBLE);
        listViewdogs = (ListView) findViewById(R.id.listViewdogsC);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        dogList = new ArrayList<>();
    }


    @Override
    protected void onStart() {
        super.onStart();
        menuForadopter = DB.getReference("dogs");
        menuForadopter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                dogList.clear();
                /*
                 * Search all dogs for matching searched
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dog dog = snapshot.getValue(Dog.class);
                    boolean isMatch = true;
                    if (dog != null && preference != null) {
                        if (preference.getAge() != null) {
                            if (dog.getAge() != preference.getAge()) {
                                isMatch = false;
                            }
                        }
                        if (preference.getSize() != null) {
                            if (dog.getSize() != preference.getSize()) {
                                isMatch = false;
                            }
                        }
                        if (preference.getGender() != null) {
                            if (dog.getGender() != preference.getGender()) {
                                isMatch = false;
                            }
                        }
                        if (preference.getRegion() != null) {
                            if (dog.getRegion() != preference.getRegion()) {
                                isMatch = false;
                            }
                        }
                        if (preference.isNeedsEducated()) {
                            if (!dog.isNeedsEducated()) {
                                isMatch = false;
                            }
                        }
                        if (preference.isHypoallergenic()) {
                            if (!dog.isHypoallergenic()) {
                                isMatch = false;
                            }
                        }
                        if (preference.isKidsFriendly()) {
                            if (!dog.isKidsFriendly()) {
                                isMatch = false;
                            }
                        }
                        if (preference.isDogsFriendly()) {
                            if (!dog.isDogsFriendly()) {
                                isMatch = false;
                            }
                        }
                        if (preference.isCatsFriendly()) {
                            if (!dog.isCatsFriendly()) {
                                isMatch = false;
                            }
                        }
                        if (preference.isSuitsToApartment()) {
                            if (!dog.isSuitsToApartment()) {
                                isMatch = false;
                            }
                        }


                        if (isMatch) {
                            dogList.add(dog);
                        }
                    }
                }
                if (dogList.isEmpty()) {
                    noResults.setVisibility(View.VISIBLE);
                    return;
                }
                DogAdapter dogAdapter = new DogAdapter(dogsResultsAdopter.this, dogList);
                listViewdogs.setAdapter(dogAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}