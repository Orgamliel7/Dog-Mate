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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.AdvertiserAdapter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * In this activity, the adopter can search a advertiser
 */
public class SearchAdvertiserActivity extends Adopter_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewBakers;
    String userID;
    DatabaseReference AdvertiserRef;
    FirebaseDatabase DB;
    FirebaseUser currentUser;
    List<Advertiser> advertiserList;
    EditText search_edit_text;
    TextView noResults; //will notify when the advertiser searched not found

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_advertiser);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        currentUser = FireLog.getCurrentUser();
        userID = currentUser.getUid();
        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        noResults =findViewById(R.id.noResults);
        noResults.setVisibility(View.INVISIBLE);
        listViewBakers = findViewById(R.id.bakersListView);

        advertiserList = new ArrayList<Advertiser>();

        AdvertiserRef = DB.getReference("Users").child("Advertiser");
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             *
             * @param s
             * Will update the list according to the sort
             */
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    noResults.setVisibility(View.INVISIBLE);
                    setAdapter(s.toString().toLowerCase().trim());
                }
                else{
                    //if there is not searched text - will again show all the bakers
                   cleanFilter();
                }
            }
        });
    }

    public void setAdapter(final String searchedText) {
        AdvertiserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                advertiserList.clear();
                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Advertiser advertiser = snapshot.getValue(Advertiser.class);
                    String full_name = advertiser.getFull_name();
                    String city = advertiser.getAddress().getCity();
                    String streetName = advertiser.getAddress().getStreetName();
                    //Adding all the bakers macthing to the search
                    if (full_name.toLowerCase().trim().contains(searchedText)) {
                        advertiserList.add(advertiser);
                    } else if (city.toLowerCase().trim().contains(searchedText)) {
                        advertiserList.add(advertiser);
                    } else if (streetName.toLowerCase().trim().contains(searchedText)) {
                        advertiserList.add(advertiser);
                    }
                }
                if(advertiserList.isEmpty()){
                    noResults.setVisibility(View.VISIBLE);
                }
                AdvertiserAdapter advertiserAdapter = new AdvertiserAdapter(SearchAdvertiserActivity.this, advertiserList);
                listViewBakers.setAdapter(advertiserAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
//showing all the bakers again
    public void cleanFilter(){
        noResults.setVisibility(View.INVISIBLE);
        AdvertiserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                advertiserList.clear();
                for (DataSnapshot bakersnapShot : dataSnapshot.getChildren()) {
                    Advertiser advertiser = bakersnapShot.getValue(Advertiser.class);
                    advertiserList.add(advertiser);
                }
                if (advertiserList.isEmpty()) {
                    Toast.makeText(SearchAdvertiserActivity.this, "התפריט ריק! הוסף כלב חדש", Toast.LENGTH_LONG).show();
                    return;
                }
                AdvertiserAdapter advertiserAdapter = new AdvertiserAdapter(SearchAdvertiserActivity.this, advertiserList);
                listViewBakers.setAdapter(advertiserAdapter);
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
        AdvertiserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                advertiserList.clear();
                for (DataSnapshot bakersnapShot : dataSnapshot.getChildren()) {
                    Advertiser advertiser = bakersnapShot.getValue(Advertiser.class);
                    advertiserList.add(advertiser);
                }
                if (advertiserList.isEmpty()) {
                    Toast.makeText(SearchAdvertiserActivity.this, "התפריט ריק! הוסף כלב חדש", Toast.LENGTH_LONG).show();
                    return;
                }
                AdvertiserAdapter advertiserAdapter = new AdvertiserAdapter(SearchAdvertiserActivity.this, advertiserList);
                listViewBakers.setAdapter(advertiserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Press on a advertiser will move to the advertiser's menu
        listViewBakers.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchAdvertiserActivity.this, AdopterMenuActivity.class);
                intent.putExtra("advertiser", advertiserList.get(i));
                startActivity(intent);
            }

        });

    }


}
