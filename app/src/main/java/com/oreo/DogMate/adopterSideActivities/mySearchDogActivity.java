package com.oreo.DogMate.adopterSideActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Age;
import com.oreo.DogMate.Objects.Gender;
import com.oreo.DogMate.Objects.Preference;
import com.oreo.DogMate.Objects.Region;
import com.oreo.DogMate.Objects.Size;
import com.oreo.DogMate.R;

public class mySearchDogActivity extends Adopter_Navigation {
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_search_dog);
        preference = new Preference();

    }


    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.search_needs_educated:
                if (checked) preference.setNeedsEducated(true);
                break;
            case R.id.search_isHypo:
                if (checked) preference.setHypoallergenic(true);
                break;
            case R.id.search_kidsFriendly:
                if (checked) preference.setKidsFriendly(true);
                break;
            case R.id.search_catsFriendly:
                if (checked) preference.setCatsFriendly(true);
                break;
            case R.id.search_dogsFriendly:
                if (checked) preference.setDogsFriendly(true);
                break;
            case R.id.search_suitsToApartment:
                if (checked) preference.setSuitsToApartment(true);
                break;

        }
    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedAge(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_puppy:
                if (checked)
                    preference.setAge(Age.PUPPY);
                break;
            case R.id.radio_adult:
                if (checked)
                    preference.setAge(Age.ADULT);
                break;

            case R.id.radio_old:
                if (checked)
                    preference.setAge(Age.OLD);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedRegion(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_center:
                if (checked)
                    preference.setRegion(Region.CENTRAL);
                break;
            case R.id.radio_north:
                if (checked)
                    preference.setRegion(Region.NORTH);
                break;

            case R.id.radio_south:
                if (checked)
                    preference.setRegion(Region.SOUTH);
                break;

            case R.id.radio_jerusalem:
                if (checked)
                    preference.setRegion(Region.JERUSALEM);
                break;
        }
    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedGender(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_female:
                if (checked)
                    preference.setGender(Gender.FEMALE);
                break;
            case R.id.radio_male:
                if (checked)
                    preference.setGender(Gender.MALE);
                break;

        }
    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedSize(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_small:
                if (checked)
                    preference.setSize(Size.SMALL);
                break;
            case R.id.radio_medium:
                if (checked)
                    preference.setSize(Size.MEDIUM);
                break;
            case R.id.radio_big:
                if (checked)
                    preference.setSize(Size.BIG);
                break;
        }
    }

    public void start(View view) {
        Intent intent = new Intent(mySearchDogActivity.this, dogsResultsAdopter.class);
        intent.putExtra("Preference", preference);
        startActivity(intent);

    }
}