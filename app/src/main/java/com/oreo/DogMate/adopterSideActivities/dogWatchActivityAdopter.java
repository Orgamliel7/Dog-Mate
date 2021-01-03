package com.oreo.DogMate.adopterSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.oreo.DogMate.ListsAdapters.DogImageAdapterAdopter;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Adopter;
import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.Objects.Upload;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This activity will show the dog chosen, it's details and pictures,
 * and will shoe a button to adoption the dog.
 */
public class dogWatchActivityAdopter extends Adopter_Navigation {
    private RecyclerView recyclerView;
    private DogImageAdapterAdopter dogImageAdapter; //adapter for the dogs images
    private DatabaseReference imageRef; //reference for the upload details in the DB
    private DatabaseReference dogRef; //reference to the dog in the Advertiser's dogs in the DB
    private FirebaseStorage storage; // reference to the picture itself in the storage
    private List<Upload> uploads; // one upload gives us one picture URL
    private ProgressBar progressBar;
    private ValueEventListener imageRefListener; //Listener for the picture
    FirebaseDatabase DB;
    private FirebaseAuth FireLog;
    String userID;
    Dog dog;
    Adopter me;
    boolean bAddDog;
    Advertiser advertiser;
    DatabaseReference adoptionsRef;
    TextView dogDetails,AdvertiserDetails, street, city, total, breed;//will show the dog and Advertisers main details
    DatabaseReference  adopterRef;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_watch_adopter);
        Intent intent = getIntent();
        dog =(Dog) intent.getSerializableExtra("Dog");
        recyclerView = findViewById(R.id.dogPicturesRecycler);
        progressBar = findViewById(R.id.progress_image_advertiser);
        dogDetails = findViewById(R.id.dogWatch);
        AdvertiserDetails = findViewById(R.id.advertiserdets);
        total = findViewById(R.id.totalPay);
        street = findViewById(R.id.advertiserStreet);
        breed = findViewById(R.id.dog_breed_watchDog);
        city = findViewById(R.id.advertiserCity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();
        dogImageAdapter = new DogImageAdapterAdopter(dogWatchActivityAdopter.this, uploads);
        recyclerView.setAdapter(dogImageAdapter);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        imageRef = DB.getReference("Advertiser's dogs").child(dog.getadvertiserID()).child(dog.getDogID()).child("images");
        storage = FirebaseStorage.getInstance();
        dogRef=DB.getReference("Advertiser's dogs").child(dog.getadvertiserID()).child(dog.getDogID());

    }
    protected void onStart() {
        super.onStart();
        adoptionsRef = DB.getReference("Users/Advertiser");
        adoptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            /**
             * Setting the details of the dog and advertiser from the DB
             */
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(dog.getadvertiserID())){
                    advertiser = dataSnapshot.child(dog.getadvertiserID()).getValue(Advertiser.class);
                    dogDetails.setText("שם הכלב: "+ dog.getName());
                    breed.setText( "גזע הכלב: " + dog.getBreed());
                    total.setText("גיל הכלב: "+ dog.getAge());
                    AdvertiserDetails.setText("פרטי המפרסם: "+ advertiser.getFull_name());
                    street.setText("אימייל: "+ advertiser.getEmail());
                    city.setText("מספר פלאפון: "+ advertiser.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Adding te pictures to the Activity
         */
        imageRefListener = imageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploads.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);

                    uploads.add(upload);

                }
                dogImageAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(dogWatchActivityAdopter.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        //If this advertiser is in the favorits - will point this out to the adopter
        adopterRef = DB.getReference("Users/Adopter").child(userID);
        adopterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Adopter.class);
                if (me != null) {
                    for (int i = 0; i < me.getFavorites().size(); i++) {
                        if (me.getFavorites().get(i).getDogID().equals(dog.getDogID())) {
                            Button button = (Button) findViewById(R.id.addtoFavorites);
                            //button.setClickable(false);
                            button.setText("הסרת כלב מהמועדפים");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     *
     * @param v - the button "adoption" - will take the user to the adoptdog activity
     *           to fill details of the adoption.
     */
        public void adoption(View v) {
            Intent intent = new Intent(dogWatchActivityAdopter.this, AdoptDogActivity.class);
            intent.putExtra("Dog", dog);
            intent.putExtra("Advertiser", advertiser);
            startActivity(intent);
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
                if (me != null) {
                    bAddDog  =  me.addDog(dog);
                    adopterRef.setValue(me, completionListener);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            final DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(getApplicationContext(), "כלב לא נוסף למועדפים!",
                                Toast.LENGTH_SHORT).show();


                    } else if(bAddDog==true){
                        Toast.makeText(getApplicationContext(), "כלב נוסף למועדפים!",
                                Toast.LENGTH_SHORT).show();
                    }else if(bAddDog==false){
                        Toast.makeText(getApplicationContext(), "כלב הוסר מהמועדפים!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };
        });
    }
}
