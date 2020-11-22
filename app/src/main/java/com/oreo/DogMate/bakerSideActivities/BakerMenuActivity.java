package com.oreo.DogMate.bakerSideActivities;

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
import com.oreo.DogMate.Navigation.Baker_Navigation;
import com.oreo.DogMate.Objects.Pastry;
import com.oreo.DogMate.R;
import com.oreo.DogMate.ListsAdapters.PastryAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * shows the baker it's pastries, and an option to add a new pastry,
 * when pressing a pastry wil move to WatchPastryBaker activity
 */
public class BakerMenuActivity extends Baker_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewPastries;
    String userID;
    DatabaseReference menu;
    FirebaseDatabase DB;

    List<Pastry> pastryList;

    //  private ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baker_menu);
        listViewPastries = (ListView) findViewById(R.id.menu);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        pastryList = new ArrayList<Pastry>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        menu = DB.getReference("Menu").child(userID);
        menu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pastryList.clear();

                for (DataSnapshot pastrySnapShot : dataSnapshot.getChildren()) {
                    Pastry pastry = pastrySnapShot.getValue(Pastry.class);
                    pastryList.add(pastry);
                }
                if (pastryList.isEmpty()) {
                    Toast.makeText(BakerMenuActivity.this, "התפריט ריק! הוסף מאפה חדש", Toast.LENGTH_LONG).show();
                    moveToAddPastry();
                    return;
                }
                PastryAdapter pastryAdapter = new PastryAdapter(BakerMenuActivity.this, pastryList);
                listViewPastries.setAdapter(pastryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listViewPastries.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BakerMenuActivity.this, WatchPastryBakerActivity.class);
                intent.putExtra("Pastry", pastryList.get(i));
                startActivity(intent);
            }
        });

    }
    public void moveToAddPastry() {
        Intent intent = new Intent(BakerMenuActivity.this, AddPastryActivity.class);
        startActivity(intent);
    }

    public void addNewPastry(View view) {
        moveToAddPastry();
    }
}