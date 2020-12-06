package com.oreo.DogMate.advertiserSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;
import com.oreo.DogMate.ListsAdapters.DogAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * shows the advertiser it's dogs, and an option to add a new dog,
 * when pressing a dog wil move to Watchdogadvertiser activity
 */
public class advertiserMenuActivity extends Advertiser_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewdogs;
    String userID;
    DatabaseReference menu;
    FirebaseDatabase DB;

    List<Dog> dogList;

    //  private ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_menu);
        listViewdogs = (ListView) findViewById(R.id.menu);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        dogList = new ArrayList<Dog>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        menu = DB.getReference("Menu").child(userID);
        menu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogList.clear();

                for (DataSnapshot dogSnapShot : dataSnapshot.getChildren()) {
                    Dog dog = dogSnapShot.getValue(Dog.class);
                    dogList.add(dog);
                }
                if (dogList.isEmpty()) {
                    Toast.makeText(advertiserMenuActivity.this, "התפריט ריק! הוסף כלב חדש", Toast.LENGTH_LONG).show();
                    moveToAdddog();
                    return;
                }
                DogAdapter dogAdapter = new DogAdapter(advertiserMenuActivity.this, dogList);
                listViewdogs.setAdapter(dogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listViewdogs.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(advertiserMenuActivity.this, WatchDogAdvertiserActivity.class);
                intent.putExtra("Dog", dogList.get(i));
                startActivity(intent);
            }
        });

    }
    public void moveToAdddog() {
        Intent intent = new Intent(advertiserMenuActivity.this, AddDogActivity.class);
        startActivity(intent);
    }

    public void addNewDog(View view) {
        moveToAdddog();
    }
}