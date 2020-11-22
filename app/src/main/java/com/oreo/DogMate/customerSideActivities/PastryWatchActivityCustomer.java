package com.oreo.DogMate.customerSideActivities;

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
import com.oreo.DogMate.ListsAdapters.PastryImageAdapterCustomer;
import com.oreo.DogMate.Navigation.Customer_Navigation;
import com.oreo.DogMate.Objects.Baker;
import com.oreo.DogMate.Objects.Pastry;
import com.oreo.DogMate.Objects.Upload;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This activity will show the pastry chosen, it's details and pictures,
 * and will shoe a button to order the pastry.
 */
public class PastryWatchActivityCustomer extends Customer_Navigation {
    private RecyclerView recyclerView;
    private PastryImageAdapterCustomer pastryImageAdapter; //adapter for the pastries images
    private DatabaseReference imageRef; //reference for the upload details in the DB
    private DatabaseReference pastryRef; //reference to the pastry in the Menu in the DB
    private FirebaseStorage storage; // reference to the picture itself in the storage
    private List<Upload> uploads; // one upload gives us one picture URL
    private ProgressBar progressBar;
    private ValueEventListener imageRefListener; //Listener for the picture
    FirebaseDatabase DB;
    private FirebaseAuth FireLog;
    String userID;
    Pastry pastry;
    Baker baker;
    DatabaseReference bakerRef;
    TextView pastryDetails,BakerDetails, street, city, total; //will show the pastry and bakers main details

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastry_watch_customer);
        Intent intent = getIntent();
        pastry =(Pastry) intent.getSerializableExtra("Pastry");
        recyclerView = findViewById(R.id.pastryPicturesRecycler);
        progressBar = findViewById(R.id.progress_image_baker);
        pastryDetails = findViewById(R.id.pastryWatch);
        BakerDetails = findViewById(R.id.bakerdets);
        total = findViewById(R.id.totalPay);
        street = findViewById(R.id.bakerStreet);
        city = findViewById(R.id.bakerCity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();
        pastryImageAdapter = new PastryImageAdapterCustomer(PastryWatchActivityCustomer.this, uploads);
        recyclerView.setAdapter(pastryImageAdapter);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        imageRef = DB.getReference("Menu").child(pastry.getBakerID()).child(pastry.getDocID()).child("images");
        storage = FirebaseStorage.getInstance();
        pastryRef=DB.getReference("Menu").child(pastry.getBakerID()).child(pastry.getDocID());

    }
    protected void onStart() {
        super.onStart();
        bakerRef = DB.getReference("Users/Bakers");
        bakerRef.addValueEventListener(new ValueEventListener() {
            @Override
            /**
             * Setting the details of the pastry and baker from the DB
             */
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(pastry.getBakerID())){
                    baker = dataSnapshot.child(pastry.getBakerID()).getValue(Baker.class);
                    pastryDetails.setText("צפייה במאפה: "+ pastry.getName());
                    total.setText("סך הכל לתשלום: "+pastry.getPrice()+".");
                    BakerDetails.setText("פרטי האופה: "+baker.getFull_name());
                    street.setText("רחוב: "+ baker.getAddress().getStreetName());
                    city.setText("עיר: "+ baker.getAddress().getCity());
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
                pastryImageAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PastryWatchActivityCustomer.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     *
     * @param v - the button "order" - will take the user to the BuyPastry activity
     *           to fill details of the order.
     */
        public void order(View v) {
            Intent intent = new Intent(PastryWatchActivityCustomer.this, BuyPastryActivity.class);
            intent.putExtra("Pastry",pastry);
            intent.putExtra("Baker",baker);
            startActivity(intent);
        }
}
