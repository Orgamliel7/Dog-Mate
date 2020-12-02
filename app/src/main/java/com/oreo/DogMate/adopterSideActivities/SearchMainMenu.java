package com.oreo.DogMate.adopterSideActivities;

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
import com.oreo.DogMate.ListsAdapters.AdoptionAdapterAdopter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * The main menu for the search - will show the last orders,
 * option to search by a dog or a advertiser
 */
public class SearchMainMenu extends Adopter_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView ordersListView;
    String userID;
    DatabaseReference adoptionsAdoRef; //reference to the orders
    FirebaseDatabase DB;
    List<Adoption> ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main_menu);
        ordersListView = (ListView)findViewById(R.id.my_last_orders_list_view);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        ordersList = new ArrayList<Adoption>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        adoptionsAdoRef = DB.getReference("Adoptions/Adopter Adoptions").child(userID);
        //Will show all the last orders of the user
        adoptionsAdoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot orderSnapShot : dataSnapshot.getChildren()) {
                    Adoption adoption = orderSnapShot.getValue(Adoption.class);
                    ordersList.add(adoption);
                }
                if (ordersList.isEmpty()) {
                    Toast.makeText(SearchMainMenu.this, "אין הזמנות בתור", Toast.LENGTH_LONG).show();
                    return;
                }
                AdoptionAdapterAdopter orderC_Adapter = new AdoptionAdapterAdopter(SearchMainMenu.this, ordersList);
                ordersListView.setAdapter(orderC_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //A click on a adoption will move to the PastryWatchActivity to re-adoption
        ordersListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchMainMenu.this, dogWatchActivityAdopter.class);
                intent.putExtra("Dog", ordersList.get(i).getDog());
                intent.putExtra("Advertiser",ordersList.get(i).getAdvertiser());
                startActivity(intent);
            }

        });
    }

    public void SearchBaker(View view) {
        startActivity(new Intent(SearchMainMenu.this, SearchAdvertiserActivity.class));
    }

    public void SearchPastry(View view) {
        startActivity(new Intent(SearchMainMenu.this, searchDogActivity.class));
    }
}
