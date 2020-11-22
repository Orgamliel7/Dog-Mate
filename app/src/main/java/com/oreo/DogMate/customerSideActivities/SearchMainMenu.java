package com.oreo.DogMate.customerSideActivities;

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
import com.oreo.DogMate.ListsAdapters.OrderAdapterCustomer;
import com.oreo.DogMate.Navigation.Customer_Navigation;
import com.oreo.DogMate.Objects.Order;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * The main menu for the search - will show the last orders,
 * option to search by a pastry or a baker
 */
public class SearchMainMenu extends Customer_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView ordersListView;
    String userID;
    DatabaseReference databaseOrdersC; //reference to the orders
    FirebaseDatabase DB;
    List<Order> ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main_menu);
        ordersListView = (ListView)findViewById(R.id.my_last_orders_list_view);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        ordersList = new ArrayList<Order>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        databaseOrdersC = DB.getReference("Orders/Customers Orders").child(userID);
        //Will show all the last orders of the user
        databaseOrdersC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot orderSnapShot : dataSnapshot.getChildren()) {
                    Order order = orderSnapShot.getValue(Order.class);
                    ordersList.add(order);
                }
                if (ordersList.isEmpty()) {
                    Toast.makeText(SearchMainMenu.this, "אין הזמנות בתור", Toast.LENGTH_LONG).show();
                    return;
                }
                OrderAdapterCustomer orderC_Adapter = new OrderAdapterCustomer(SearchMainMenu.this, ordersList);
                ordersListView.setAdapter(orderC_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //A click on a order will move to the PastryWatchActivity to re-order
        ordersListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchMainMenu.this, PastryWatchActivityCustomer.class);
                intent.putExtra("Pastry", ordersList.get(i).getPastry());
                intent.putExtra("Baker",ordersList.get(i).getBaker());
                startActivity(intent);
            }

        });
    }

    public void SearchBaker(View view) {
        startActivity(new Intent(SearchMainMenu.this,SearchBakerActivity.class));
    }

    public void SearchPastry(View view) {
        startActivity(new Intent(SearchMainMenu.this,SearchPastryActivity.class));
    }
}
