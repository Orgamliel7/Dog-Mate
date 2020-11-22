package com.oreo.DogMate.customerSideActivities;

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
import com.oreo.DogMate.ListsAdapters.BakerAdapter;
import com.oreo.DogMate.Navigation.Customer_Navigation;
import com.oreo.DogMate.Objects.Baker;
import com.oreo.DogMate.Objects.Customer;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * This Activity contains all the favorites bakers that the user have chosen.
 *
 */
public class Favorites extends Customer_Navigation {
    private FirebaseAuth FireLog;// fire base authentication - will give us the user ID
    ListView listViewBakers; // The list view that will show the bakers
    String userID;
    DatabaseReference customerRef; //A reference to the customer's favorites arrayList in the DB
    FirebaseDatabase DB; // A reference to our DataBase
    FirebaseUser currentUser; //A reference to the current user
    List<Baker> bakerList; // the list that will contain the bakers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        currentUser = FireLog.getCurrentUser();
        userID = currentUser.getUid();
        listViewBakers = findViewById(R.id.bakers_list);
        bakerList = new ArrayList<Baker>();
        customerRef = DB.getReference("Cutsomers").child(userID).child("favorites");

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        customerRef = DB.getReference("Users/Customers").child(userID);
        /**
         * Adding the favorites bakers to the bakerList.
         */
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bakerList.clear();
                Customer me =  dataSnapshot.getValue(Customer.class);
                    for (Baker baker : me.getFavorites()) {
                        bakerList.add(baker);
                    }

                if (bakerList.isEmpty()) {
                    Toast.makeText(Favorites.this, "אין מועדפים", Toast.LENGTH_LONG).show();
                    return;
                }
                BakerAdapter bakerAdapter = new BakerAdapter(Favorites.this, bakerList);
                listViewBakers.setAdapter(bakerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /**
         * when the customer will press one baker,
         * the activity of the pastries of this baker will be opened
         */
        listViewBakers.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Favorites.this, CustomerMenuActivity.class);
                intent.putExtra("baker", bakerList.get(i));
                startActivity(intent);
            }
        });
    }
}