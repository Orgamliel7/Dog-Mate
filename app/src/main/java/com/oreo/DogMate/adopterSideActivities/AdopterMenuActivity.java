package com.oreo.DogMate.adopterSideActivities;

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
import com.oreo.DogMate.ListsAdapters.DogAdapter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Adopter;
import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Shows a menu of a advertiser,
 * a press on a dog will move to adoption this dog
 */
public class AdopterMenuActivity extends Adopter_Navigation {

    private FirebaseAuth FireLog;// fire base authentication
    ListView listViewdogs;
    String userID;
    DatabaseReference menuForadopter, adopterRef;
    FirebaseDatabase DB;
    List<Dog> dogList;
    Advertiser advertiser;
    Adopter me;
    EditText search_edit_text;
    TextView noResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_menu);
        Intent intent = getIntent();
        advertiser = (Advertiser) intent.getSerializableExtra("advertiser");
        search_edit_text = (EditText) findViewById(R.id.search_edit_text2);
        noResults = findViewById(R.id.noResults2);
        noResults.setVisibility(View.INVISIBLE);
        listViewdogs = (ListView) findViewById(R.id.listViewdogsC);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        dogList = new ArrayList<Dog>();
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
        menuForadopter = DB.getReference("Menu").child(advertiser.getUserID());
        menuForadopter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogList.clear();
                for (DataSnapshot dogSnapShot : dataSnapshot.getChildren()) {
                    Dog dog = dogSnapShot.getValue(Dog.class);
                    dogList.add(dog);
                }

                DogAdapter dogAdapter = new DogAdapter(AdopterMenuActivity.this, dogList);
                listViewdogs.setAdapter(dogAdapter);
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
        menuForadopter = DB.getReference("Menu").child(advertiser.getUserID());
        menuForadopter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogList.clear();
                for (DataSnapshot dogSnapShot : dataSnapshot.getChildren()) {
                    Dog dog = dogSnapShot.getValue(Dog.class);
                    dogList.add(dog);
                }

                DogAdapter dogAdapter = new DogAdapter(AdopterMenuActivity.this, dogList);
                listViewdogs.setAdapter(dogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //If this advertiser is in the favorits - will point this out to the adopter
        adopterRef = DB.getReference("Users/Adopter").child(userID);
        adopterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Adopter.class);
                for (int i = 0; i < me.getFavorites().size(); i++) {
                    if(me.getFavorites().get(i).getUserID().equals(advertiser.getUserID())){
                        Button button = (Button) findViewById(R.id.addtoFavorites);
                        button.setClickable(false);
                        button.setText("זהו מפרסם מועדף עליי!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewdogs.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AdopterMenuActivity.this, dogWatchActivityAdopter.class);
                intent.putExtra("Dog", dogList.get(i));
                intent.putExtra("Advertiser", advertiser);
                startActivity(intent);
            }

        });

    }

    /**
     * shows the search according to the searched text
     * @param searchedText
     */
    public void setAdapter(final String searchedText) {
        menuForadopter = DB.getReference("Menu").child(advertiser.getUserID());
        menuForadopter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                dogList.clear();
                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dog dog = snapshot.getValue(Dog.class);
                    String name = dog.getName();
                    if (name.toLowerCase().trim().contains(searchedText)) {
                        dogList.add(dog);
                    }
                }
                if (dogList.isEmpty()) {
                    noResults.setVisibility(View.VISIBLE);
                }
                DogAdapter dogAdapter = new DogAdapter(AdopterMenuActivity.this, dogList);
                listViewdogs.setAdapter(dogAdapter);
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

        adopterRef = DB.getReference("Users/Adopter").child(userID);
        adopterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Adopter.class);
                me.addAdvertiser(advertiser);
                adopterRef.setValue(me, completionListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(getApplicationContext(), "מפרסם לא נוסף למועדפים!",
                                Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getApplicationContext(), "מפרסם נוסף למועדפים!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };
        });
    }
}
