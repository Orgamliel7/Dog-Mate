package com.oreo.DogMate.adopterSideActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.Objects.Upload;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
    private DatabaseReference dogRef; //reference to the dog in the Menu in the DB
    private FirebaseStorage storage; // reference to the picture itself in the storage
    private List<Upload> uploads; // one upload gives us one picture URL
    private ProgressBar progressBar;
    private ValueEventListener imageRefListener; //Listener for the picture
    FirebaseDatabase DB;
    private FirebaseAuth FireLog;
    String userID;
    Dog dog;
    Advertiser advertiser;
    DatabaseReference adoptionsRef;
    TextView dogDetails,AdvertiserDetails, street, city, total; //will show the dog and Advertisers main details

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
        imageRef = DB.getReference("Menu").child(dog.getadvertiserID()).child(dog.getDocID()).child("images");
        storage = FirebaseStorage.getInstance();
        dogRef=DB.getReference("Menu").child(dog.getadvertiserID()).child(dog.getDocID());

    }
    protected void onStart() {
        super.onStart();
        adoptionsRef = DB.getReference("Users/Adoptions");
        adoptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            /**
             * Setting the details of the dog and advertiser from the DB
             */
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(dog.getadvertiserID())){
                    advertiser = dataSnapshot.child(dog.getadvertiserID()).getValue(Advertiser.class);
                    dogDetails.setText("צפייה בכלב: "+ dog.getName());
                    total.setText("סך הכל לתשלום: "+ dog.getPrice()+".");
                    AdvertiserDetails.setText("פרטי המפרסם: "+ advertiser.getFull_name());
                    street.setText("רחוב: "+ advertiser.getAddress().getStreetName());
                    city.setText("עיר: "+ advertiser.getAddress().getCity());
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

    }

    /**
     *
     * @param v - the button "adoption" - will take the user to the Buydog activity
     *           to fill details of the adoption.
     */
        public void order(View v) {
            Intent intent = new Intent(dogWatchActivityAdopter.this, BuyDogActivity.class);
            intent.putExtra("Dog", dog);
            intent.putExtra("Advertiser", advertiser);
            startActivity(intent);
        }
}
