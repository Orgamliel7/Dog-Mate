package com.oreo.DogMate.ListsAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DogAdapter extends ArrayAdapter<Dog> {
    private final Activity context;
    private final List<Dog> dogs;

    public DogAdapter(Activity context, List<Dog> dogs) {
        super(context, R.layout.list_dog_item, dogs);
        this.context = context;
        this.dogs = dogs;

    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.list_dog_item, null, true);

        TextView name = listViewItem.findViewById(R.id.dogName);
        TextView age = listViewItem.findViewById(R.id.age);
        TextView region = listViewItem.findViewById(R.id.region);
        TextView gender = listViewItem.findViewById(R.id.gender);
        final ImageView imageView = listViewItem.findViewById(R.id.img);

        Dog dog = dogs.get(position);
        final boolean[] success = {false};

        if (dog.getImages().size() > 0) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl("gs://dogmate-c8039.appspot.com/Menu").child(dog.getadvertiserID() + "/" + dog.getDogID() + "/" + dog.getImages().get(0).getName() + ".jpg");

            try {
                final File file = File.createTempFile("image", "jpg");
                storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                        success[0] = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (!success[0]) {
                FirebaseStorage storage1 = FirebaseStorage.getInstance();
                StorageReference storageReference1 = storage1.getReferenceFromUrl("gs://dogmate-c8039.appspot.com/Menu").child(dog.getadvertiserID() + "/" + dog.getDogID() + "/" + dog.getImages().get(0).getName() + ".png");

                try {
                    final File file = File.createTempFile("image", "png");
                    storageReference1.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                            success[0] = true;
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        age.setText("גיל: " + dog.getAge().name());
        name.setText("שם: " + dog.getName());
        region.setText("איזור בארץ: " + dog.getRegion().name());
        gender.setText("מין: " + dog.getGender().name());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if (dogs == null) return 0;
        else return dogs.size();
    }

}
