package com.oreo.DogMate.adopterSideActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.oreo.DogMate.R;

public class mySearchDogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_search_dog);


    }

    public void start(View view) {
        startActivity(new Intent(mySearchDogActivity.this, searchDogActivity.class));

    }
}