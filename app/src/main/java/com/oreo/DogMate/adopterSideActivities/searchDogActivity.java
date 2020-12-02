package com.oreo.DogMate.adopterSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.DogAdapter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Search by dog - a list of all the pastries from the "Pastries" in the DB
 */
public class searchDogActivity extends Adopter_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewPastries;
    String userID;
    DatabaseReference menuForCustomer;
    FirebaseDatabase DB;
    List<Dog> dogList;
    EditText search_edit_text;
    TextView noResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog);search_edit_text = (EditText) findViewById(R.id.search_edit_text2);
        noResults = findViewById(R.id.noResults2);
        noResults.setVisibility(View.INVISIBLE);
        listViewPastries = (ListView) findViewById(R.id.listViewPastriesC);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        dogList = new ArrayList<Dog>();
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * Option to search a dog by it's name
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    noResults.setVisibility(View.INVISIBLE);
                    setAdapter(s.toString().toLowerCase().trim());
                }
                else{
                    cleanFilter();
                }
            }
        });
    }

    private void cleanFilter() {
        userID = FireLog.getCurrentUser().getUid();
        noResults.setVisibility(View.INVISIBLE);
        menuForCustomer = DB.getReference("Pastries");
        menuForCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogList.clear();
                for (DataSnapshot pastrySnapShot : dataSnapshot.getChildren()) {
                    Dog dog = pastrySnapShot.getValue(Dog.class);
                    dogList.add(dog);
                }

                DogAdapter dogAdapter = new DogAdapter(searchDogActivity.this, dogList);
                listViewPastries.setAdapter(dogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        menuForCustomer = DB.getReference("Pastries");
        menuForCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogList.clear();
                for (DataSnapshot pastrySnapShot : dataSnapshot.getChildren()) {
                    Dog dog = pastrySnapShot.getValue(Dog.class);
                    dogList.add(dog);
                }

                DogAdapter dogAdapter = new DogAdapter(searchDogActivity.this, dogList);
                listViewPastries.setAdapter(dogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewPastries.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(searchDogActivity.this, dogWatchActivityAdopter.class);
                intent.putExtra("Dog", dogList.get(i));
                startActivity(intent);
            }
        });

    }
    public void setAdapter(final String searchedText) {
        menuForCustomer = DB.getReference("Pastries");
        menuForCustomer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                dogList.clear();
                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dog dog = snapshot.getValue(Dog.class);
                    String name = dog.getName();
                    if (name.toLowerCase().trim().contains(searchedText)) {
                        dogList.add(dog);
                    }
                }
                if(dogList.isEmpty()){
                    noResults.setVisibility(View.VISIBLE);
                }
                DogAdapter dogAdapter = new DogAdapter(searchDogActivity.this, dogList);
                listViewPastries.setAdapter(dogAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
