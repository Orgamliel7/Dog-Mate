package com.oreo.DogMate.bakerSideActivities;

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
import com.oreo.DogMate.ListsAdapters.PastryImageAdapterBaker;
import com.oreo.DogMate.Navigation.Baker_Navigation;
import com.oreo.DogMate.Objects.Pastry;
import com.oreo.DogMate.Objects.Upload;
import com.oreo.DogMate.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * In this activity the baker can watch a certain pastry in his menu,
 * and edit or delete it or delete photos of it
 */
public class WatchPastryBakerActivity extends Baker_Navigation implements PastryImageAdapterBaker.OnItemClickListener {
    private RecyclerView recyclerView;
    private PastryImageAdapterBaker pastryImageAdapter;
    private DatabaseReference imageRef;
    private DatabaseReference pastryRef;
    private FirebaseStorage storage;
    private List<Upload> uploads;
    private ProgressBar progressBar;
    private ValueEventListener imageRefListener;
    FirebaseDatabase DB;
    private FirebaseAuth FireLog;
    String userID;
    Pastry pastry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_pastry_baker);
        Intent intent = getIntent();
        pastry =(Pastry) intent.getSerializableExtra("Pastry");
        recyclerView = findViewById(R.id.pastryPicturesRecycler);
        progressBar = findViewById(R.id.progress_image_baker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();
        pastryImageAdapter = new PastryImageAdapterBaker(WatchPastryBakerActivity.this, uploads);
        recyclerView.setAdapter(pastryImageAdapter);
        pastryImageAdapter.setOnItemClickListener(WatchPastryBakerActivity.this);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        imageRef = DB.getReference("Menu").child(userID).child(pastry.getDocID()).child("images");
        storage = FirebaseStorage.getInstance();
        pastryRef=DB.getReference("Menu").child(userID).child(pastry.getDocID());




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
                pastryImageAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(WatchPastryBakerActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(WatchPastryBakerActivity.this," תמונה נמחקה בהצלחה!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WatchPastryBakerActivity.this,"המחיקה נכשלה", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addPictures(View view) {
        Intent intent = new Intent(WatchPastryBakerActivity.this, addPatryPicturesActivity.class);
        intent.putExtra("Pastry",pastry);
        startActivity(intent);
    }
//contains a dialog to ask if the baker is sure he wants to delete the pastry
    public void deletePastry(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("מחיקת מאפה");
        builder.setMessage("האם אתה בטוח שברצונך למחוק מאפה זה?");
        builder.setPositiveButton("מחק",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0 ; i < pastry.getImages().size();i++){
                            onDeleteClick(i);
                        }
                        pastryRef.removeValue();
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

    public void editPastry(View view) {
        Intent intent = new Intent(WatchPastryBakerActivity.this, AddPastryActivity.class);
        intent.putExtra("Pastry", pastry);
        startActivity(intent);
    }
}
