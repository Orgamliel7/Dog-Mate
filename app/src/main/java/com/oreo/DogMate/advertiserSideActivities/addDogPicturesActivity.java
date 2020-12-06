package com.oreo.DogMate.advertiserSideActivities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.Objects.Upload;
import com.oreo.DogMate.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * In this activity, the advertiser is adding pictures for his dog
 */
public class addDogPicturesActivity extends Advertiser_Navigation {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button uploadButton;
    private Button chooseFileButton;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference picRef,dogsRef;
    private StorageTask uploadTask;
    FirebaseDatabase DB;
    private FirebaseAuth FireLog;
    String userID;
    Dog dog;
    long now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog_pictures);
        Intent intent = getIntent();
        dog =(Dog) intent.getSerializableExtra("Dog");
        uploadButton = (Button) findViewById(R.id.uploadButton);
        chooseFileButton = findViewById(R.id.chooseFile);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBarPicture);
        DB = FirebaseDatabase.getInstance();
        FireLog = FirebaseAuth.getInstance();
        userID = FireLog.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Menu").child(userID).child(dog.getDocID());
        picRef = DB.getReference("Menu").child(userID).child(dog.getDocID());
        dogsRef = DB.getReference("dogs").child(dog.getDocID());



        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask!=null && uploadTask.isInProgress()){
                    //uploadButton.setEnabled(false);
                    Toast.makeText(addDogPicturesActivity.this,"Upload in progress ", Toast.LENGTH_LONG).show();
                }
                else {
                    uploadImage();
                }
            }
        });

    }
    private String getFileExtension(Uri uri){
        ContentResolver cr  = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        if(imageUri!=null){
             now = System.currentTimeMillis();
            StorageReference fileRef = storageReference.child(now+ "."
                    + getFileExtension(imageUri));
            uploadTask = fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(addDogPicturesActivity.this,"Upload Succesfull ", Toast.LENGTH_LONG).show();
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uri.isComplete());
                    Uri url = uri.getResult();
                    Upload upload = new Upload(now + "",url.toString());
                    dog.addImage(upload);
                    picRef.setValue(dog);
                    dogsRef.setValue(dog);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addDogPicturesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()
                            /taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                }
            });


        }
        else{
            Toast.makeText(this, " no file selected ", Toast.LENGTH_LONG).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData()!=null){
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }
    public void backToMenu(View view) {
        if(dog.getImages().isEmpty()){
            Toast.makeText(this, " לא הוספו תמונות! ", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(addDogPicturesActivity.this, advertiserMenuActivity.class);
        startActivity(intent);
    }
}
