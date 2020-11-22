package com.oreo.DogMate.bakerSideActivities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.orderAdapterBaker;
import com.oreo.DogMate.Navigation.Baker_Navigation;
import com.oreo.DogMate.Objects.Order;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * In this activity, the baker can watch his orders and their details
 */
public class BakerOrderActivity extends Baker_Navigation {

    private FirebaseAuth FireLog;// fire base authentication
    ListView listOrders;
    String userID;
    DatabaseReference databaseOrdersB;
    FirebaseDatabase DB;

    List<Order> ordersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baker_order);
        listOrders = (ListView)findViewById(R.id.listOrders);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        ordersList = new ArrayList<Order>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        databaseOrdersB = DB.getReference("Orders/Bakers Orders").child(userID);

        databaseOrdersB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot orderSnapShot : dataSnapshot.getChildren()) {
                    Order order = orderSnapShot.getValue(Order.class);
                    ordersList.add(order);
                }
                if (ordersList.isEmpty()) {
                    Toast.makeText(BakerOrderActivity.this, "אין הזמנות בתור", Toast.LENGTH_LONG).show();
                    return;
                }
                orderAdapterBaker orderAdapter = new orderAdapterBaker(BakerOrderActivity.this, ordersList);
                listOrders.setAdapter(orderAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


    });

    }
}
