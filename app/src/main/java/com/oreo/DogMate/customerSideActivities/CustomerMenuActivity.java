package com.oreo.DogMate.customerSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.ListsAdapters.PastryAdapter;
import com.oreo.DogMate.Navigation.Customer_Navigation;
import com.oreo.DogMate.Objects.Baker;
import com.oreo.DogMate.Objects.Customer;
import com.oreo.DogMate.Objects.Pastry;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Shows a menu of a baker,
 * a press on a pastry will move to order this pastry
 */
public class CustomerMenuActivity extends Customer_Navigation {

    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewPastries;
    String userID;
    DatabaseReference menuForCustomer, customerRef;
    FirebaseDatabase DB;
    List<Pastry> pastryList;
    Baker baker;
    Customer me;
    EditText search_edit_text;
    TextView noResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
        Intent intent = getIntent();
        baker = (Baker) intent.getSerializableExtra("baker");
        search_edit_text = (EditText) findViewById(R.id.search_edit_text2);
        noResults = findViewById(R.id.noResults2);
        noResults.setVisibility(View.INVISIBLE);
        listViewPastries = (ListView) findViewById(R.id.listViewPastriesC);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        pastryList = new ArrayList<Pastry>();
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    noResults.setVisibility(View.INVISIBLE);
                    setAdapter(s.toString().toLowerCase().trim());
                } else {
                    cleanFilter();
                }
            }
        });
    }

    private void cleanFilter() {
        userID = FireLog.getCurrentUser().getUid();
        menuForCustomer = DB.getReference("Menu").child(baker.getUserID());
        menuForCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pastryList.clear();
                for (DataSnapshot pastrySnapShot : dataSnapshot.getChildren()) {
                    Pastry pastry = pastrySnapShot.getValue(Pastry.class);
                    pastryList.add(pastry);
                }

                PastryAdapter pastryAdapter = new PastryAdapter(CustomerMenuActivity.this, pastryList);
                listViewPastries.setAdapter(pastryAdapter);
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
        menuForCustomer = DB.getReference("Menu").child(baker.getUserID());
        menuForCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pastryList.clear();
                for (DataSnapshot pastrySnapShot : dataSnapshot.getChildren()) {
                    Pastry pastry = pastrySnapShot.getValue(Pastry.class);
                    pastryList.add(pastry);
                }

                PastryAdapter pastryAdapter = new PastryAdapter(CustomerMenuActivity.this, pastryList);
                listViewPastries.setAdapter(pastryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //If this baker is in the favorits - will point this out to the customer
        customerRef = DB.getReference("Users/Customers").child(userID);
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Customer.class);
                for (int i = 0; i < me.getFavorites().size(); i++) {
                    if(me.getFavorites().get(i).getUserID().equals(baker.getUserID())){
                        Button button = (Button) findViewById(R.id.addtoFavorites);
                        button.setClickable(false);
                        button.setText("זהו אופה מועדף עליי!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewPastries.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CustomerMenuActivity.this, PastryWatchActivityCustomer.class);
                intent.putExtra("Pastry", pastryList.get(i));
                intent.putExtra("Baker", baker);
                startActivity(intent);
            }

        });

    }

    /**
     * shows the search according to the searched text
     * @param searchedText
     */
    public void setAdapter(final String searchedText) {
        menuForCustomer = DB.getReference("Menu").child(baker.getUserID());
        menuForCustomer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                pastryList.clear();
                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pastry pastry = snapshot.getValue(Pastry.class);
                    String name = pastry.getName();
                    if (name.toLowerCase().trim().contains(searchedText)) {
                        pastryList.add(pastry);
                    }
                }
                if (pastryList.isEmpty()) {
                    noResults.setVisibility(View.VISIBLE);
                }
                PastryAdapter pastryAdapter = new PastryAdapter(CustomerMenuActivity.this, pastryList);
                listViewPastries.setAdapter(pastryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * When pressing the button "add to favorites"
     * @param view
     */
    public void addToFavorits(View view) {

        customerRef = DB.getReference("Users/Customers").child(userID);
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Customer.class);
                me.addBaker(baker);
                customerRef.setValue(me, completionListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(getApplicationContext(), "אופה לא נוסף למועדפים!",
                                Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getApplicationContext(), "אופה נוסף למועדפים!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };
        });
    }
}
