package com.oreo.DogMate.adopterSideActivities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.ListsAdapters.AdoptionAdapterAdopter;
import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * In this activity, the adopter can watch his orders
 */
public class AdopterOrderActivity extends Adopter_Navigation {

    private FirebaseAuth FireLog;// fire base authentication
    ListView ordersListView;
    String userID;
    DatabaseReference databaseOrdersC;
    FirebaseDatabase DB;

    List<Adoption> adoptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_order);
        ordersListView = (ListView)findViewById(R.id.listOrdersadopter);

        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();

        adoptionList = new ArrayList<Adoption>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userID = FireLog.getCurrentUser().getUid();
        databaseOrdersC = DB.getReference("Adoptions/Adopter Adoptions").child(userID);

        databaseOrdersC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adoptionList.clear();
                for (DataSnapshot orderSnapShot : dataSnapshot.getChildren()) {
                    Adoption adoption = orderSnapShot.getValue(Adoption.class);
                    adoptionList.add(adoption);
                }
                if (adoptionList.isEmpty()) {
                    Toast.makeText(AdopterOrderActivity.this, "אין כלבים במאגר", Toast.LENGTH_LONG).show();
                    return;
                }
                AdoptionAdapterAdopter orderC_Adapter = new AdoptionAdapterAdopter(AdopterOrderActivity.this, adoptionList);
                ordersListView.setAdapter(orderC_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
