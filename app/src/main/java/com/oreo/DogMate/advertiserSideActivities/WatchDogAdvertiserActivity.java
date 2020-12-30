package com.oreo.DogMate.advertiserSideActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oreo.DogMate.ListsAdapters.DogImageAdapterAdvertiser;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.Objects.Upload;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * In this activity the advertiser can watch a certain dog in his menu,
 * and edit or delete it or delete photos of it
 */
public class WatchDogAdvertiserActivity extends Advertiser_Navigation implements DogImageAdapterAdvertiser.OnItemClickListener {
    private RecyclerView recyclerView;
    private DogImageAdapterAdvertiser dogImageAdapter;
    private DatabaseReference imageRef;
    private DatabaseReference menuRef;
    private DatabaseReference dogRef;
    private FirebaseStorage storage;
    private List<Upload> uploads;
    private ProgressBar progressBar;
    private ValueEventListener imageRefListener;
    FirebaseDatabase DB;
    private FirebaseAuth FireLog;
    String userID;
    Dog dog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_dog_advertiser);
        Intent intent = getIntent();
        dog =(Dog) intent.getSerializableExtra("Dog");
        recyclerView = findViewById(R.id.dogPicturesRecycler);
        progressBar = findViewById(R.id.progress_image_advertiser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();
        dogImageAdapter = new DogImageAdapterAdvertiser(WatchDogAdvertiserActivity.this, uploads);
        recyclerView.setAdapter(dogImageAdapter);
        dogImageAdapter.setOnItemClickListener(WatchDogAdvertiserActivity.this);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        imageRef = DB.getReference("Menu").child(userID).child(dog.getDogID()).child("images");
        storage = FirebaseStorage.getInstance();
        menuRef =DB.getReference("Menu").child(userID).child(dog.getDogID());
        dogRef=DB.getReference("Dogs").child(dog.getDogID());




    }

    @Override
    protected void onStart() {
        super.onStart();
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
                Toast.makeText(WatchDogAdvertiserActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"לחיצה רגילה", Toast.LENGTH_SHORT).show();

    }

    /**
     * removing a certain photo the was deleted
     * @param position
     */
    @Override
    public void onDeleteClick(int position) {
        Upload selectedITem = uploads.get(position);
        final int key = position;
        StorageReference imageReference = storage.getReferenceFromUrl(selectedITem.getImageURL());
        imageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                imageRef.child(String.valueOf(key)).removeValue();
                Toast.makeText(WatchDogAdvertiserActivity.this," תמונה נמחקה בהצלחה!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WatchDogAdvertiserActivity.this,"המחיקה נכשלה", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addPictures(View view) {
        Intent intent = new Intent(WatchDogAdvertiserActivity.this, addDogPicturesActivity.class);
        intent.putExtra("Dog", dog);
        startActivity(intent);
    }
//contains a dialog to ask if the advertiser is sure he wants to delete the dog
    public void deletedog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("מחיקת כלב");
        builder.setMessage("האם אתה בטוח שברצונך למחוק כלב זה?");
        builder.setPositiveButton("מחק",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < dog.getImages().size(); i++){
                            onDeleteClick(i);
                        }
                        menuRef.removeValue();
                        dogRef.removeValue();
                    }
                });
        builder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editdog(View view) {
        Intent intent = new Intent(WatchDogAdvertiserActivity.this, AddDogActivity.class);
        intent.putExtra("Dog", dog);
        startActivity(intent);
    }
}
