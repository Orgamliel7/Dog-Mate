package com.oreo.DogMate.advertiserSideActivities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.AdoptionAdapterAdvertiser;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * In this activity, the Advertiser can watch his orders and their details
 */
public class advertiserOrderActivity extends Advertiser_Navigation {

    private FirebaseAuth FireLog;// fire base authentication
    ListView listOrders;
    String userID;
    DatabaseReference adoptionsAdvRef;
    FirebaseDatabase DB;

    List<Adoption> ordersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_order);
        listOrders = (ListView)findViewById(R.id.listOrders);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        ordersList = new ArrayList<Adoption>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        adoptionsAdvRef = DB.getReference("Adoptions/Advertiser Adoptions").child(userID);

        adoptionsAdvRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot orderSnapShot : dataSnapshot.getChildren()) {
                    Adoption adoption = orderSnapShot.getValue(Adoption.class);
                    ordersList.add(adoption);
                }
                if (ordersList.isEmpty()) {
                    Toast.makeText(advertiserOrderActivity.this, "אין הזמנות בתור", Toast.LENGTH_LONG).show();
                    return;
                }
                AdoptionAdapterAdvertiser orderAdapter = new AdoptionAdapterAdvertiser(advertiserOrderActivity.this, ordersList);
                listOrders.setAdapter(orderAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


    });

    }
}
