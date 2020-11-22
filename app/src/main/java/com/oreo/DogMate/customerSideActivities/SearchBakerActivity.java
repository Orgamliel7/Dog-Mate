package com.oreo.DogMate.customerSideActivities;

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
import com.oreo.DogMate.ListsAdapters.BakerAdapter;
import com.oreo.DogMate.Navigation.Customer_Navigation;
import com.oreo.DogMate.R;
import com.oreo.DogMate.Objects.Baker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * In this activity, the customer can search a baker
 */
public class SearchBakerActivity extends Customer_Navigation {
    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewBakers;
    String userID;
    DatabaseReference bakersRef;
    FirebaseDatabase DB;
    FirebaseUser currentUser;
    List<Baker>  bakerList;
    EditText search_edit_text;
    TextView noResults; //will notify when the baker searched not found

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_baker);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        currentUser = FireLog.getCurrentUser();
        userID = currentUser.getUid();
        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        noResults =findViewById(R.id.noResults);
        noResults.setVisibility(View.INVISIBLE);
        listViewBakers = findViewById(R.id.bakersListView);

        bakerList = new ArrayList<Baker>();

        bakersRef = DB.getReference("Users").child("Bakers");
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
        bakersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                bakerList.clear();
                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Baker baker = snapshot.getValue(Baker.class);
                    String full_name = baker.getFull_name();
                    String city = baker.getAddress().getCity();
                    String streetName = baker.getAddress().getStreetName();
                    //Adding all the bakers macthing to the search
                    if (full_name.toLowerCase().trim().contains(searchedText)) {
                        bakerList.add(baker);
                    } else if (city.toLowerCase().trim().contains(searchedText)) {
                        bakerList.add(baker);
                    } else if (streetName.toLowerCase().trim().contains(searchedText)) {
                        bakerList.add(baker);
                    }
                }
                if(bakerList.isEmpty()){
                    noResults.setVisibility(View.VISIBLE);
                }
                BakerAdapter bakerAdapter = new BakerAdapter(SearchBakerActivity.this, bakerList);
                listViewBakers.setAdapter(bakerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
//showing all the bakers again
    public void cleanFilter(){
        noResults.setVisibility(View.INVISIBLE);
        bakersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bakerList.clear();
                for (DataSnapshot bakersnapShot : dataSnapshot.getChildren()) {
                    Baker baker = bakersnapShot.getValue(Baker.class);
                    bakerList.add(baker);
                }
                if (bakerList.isEmpty()) {
                    Toast.makeText(SearchBakerActivity.this, "התפריט ריק! הוסף מאפה חדש", Toast.LENGTH_LONG).show();
                    return;
                }
                BakerAdapter bakerAdapter = new BakerAdapter(SearchBakerActivity.this, bakerList);
                listViewBakers.setAdapter(bakerAdapter);
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
        bakersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bakerList.clear();
                for (DataSnapshot bakersnapShot : dataSnapshot.getChildren()) {
                    Baker baker = bakersnapShot.getValue(Baker.class);
                    bakerList.add(baker);
                }
                if (bakerList.isEmpty()) {
                    Toast.makeText(SearchBakerActivity.this, "התפריט ריק! הוסף מאפה חדש", Toast.LENGTH_LONG).show();
                    return;
                }
                BakerAdapter bakerAdapter = new BakerAdapter(SearchBakerActivity.this, bakerList);
                listViewBakers.setAdapter(bakerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Press on a baker will move to the baker's menu
        listViewBakers.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchBakerActivity.this, CustomerMenuActivity.class);
                intent.putExtra("baker", bakerList.get(i));
                startActivity(intent);
            }

        });

    }


}
