package com.oreo.DogMate.advertiserSideActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oreo.DogMate.Navigation.Advertiser_Navigation;
import com.oreo.DogMate.Objects.Age;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.Objects.Gender;
import com.oreo.DogMate.Objects.Region;
import com.oreo.DogMate.Objects.Size;
import com.oreo.DogMate.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;


/**
 * In this activity, the advertiser can add a new dog or edit an existing one
 */
public class AddDogActivity extends Advertiser_Navigation {
    public static final String TAG = "TAG_ADD_dog";
    String userID;
    String ageIn, nameIn, descIn, allergicIn;
    private FirebaseAuth FireLog;// fire base authentication
    EditText name;
    DatabaseReference dogRef;
    FirebaseDatabase DB;
    Age agei;
    Size size;
    Gender gender;
    Region region;
    String dogName;
    boolean isNeedsEducated;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        FireLog = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance();
        dogRef = DB.getReference("Menu/" + Objects.requireNonNull(FireLog.getCurrentUser()).getUid());
        FirebaseUser user = FireLog.getCurrentUser();
        userID = user.getUid();
        name = (EditText) findViewById(R.id.dog_name_editText);

        Intent intent = getIntent();
        if (intent.hasExtra("Dog")) {
            dog = (Dog) intent.getSerializableExtra("Dog");

        }

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * Option to search a dog by it's name
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    dogName = s.toString().trim();
                } else {
                    dogName = " ";
                }
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox_needs_educated:
                if (checked) {isNeedsEducated = true;}



        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedAge(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_puppy_Adver:
                if (checked)
                    agei = Age.PUPPY;
                break;
            case R.id.radio_adult_Adver:
                if (checked)
                    agei = Age.ADULT;
                break;

            case R.id.radio_old_Adver:
                if (checked)
                    agei = Age.OLD;
                break;
            default:
                agei = Age.ADULT;

        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedRegion(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_center_Adver:
                if (checked)
                    region = Region.CENTRAL;
                break;
            case R.id.radio_north_Adver:
                if (checked)
                    region = Region.NORTH;
                break;

            case R.id.radio_south_Adver:
                if (checked)
                    region = Region.SOUTH;
                break;

            case R.id.radio_jerusalem_Adver:
                if (checked)
                    region = Region.JERUSALEM;
                break;
            default:
                region = Region.CENTRAL;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedGender(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_female_Adver:
                if (checked)
                    gender = Gender.FEMALE;
                break;
            case R.id.radio_male_Adver:
                if (checked)
                    gender = Gender.MALE;
                break;
            default:
                gender = Gender.MALE;
        }
    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClickedSize(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_small_Adver:
                if (checked)
                    size = Size.SMALL;
                break;
            case R.id.radio_medium_Adver:
                if (checked)
                    size = Size.MEDIUM;
                break;
            case R.id.radio_big_Adver:
                if (checked)
                    size = Size.BIG;
                break;
            default:
                size = Size.MEDIUM;
        }
    }


    public void addItem(View view) {

        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), "הוספת כלב נכשלה",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "כלב נוסף בהצלחה!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        if (dog == null) {
            dog = new Dog(agei, dogName, size, gender, region, isNeedsEducated, " ", " ", userID);
            dog.setDogID(dogRef.push().getKey());
        }
        //if the dog exist and we just want to edit it
        else {
            dog.setAge(agei);
            dog.setSize(size);
            dog.setGender(gender);
            dog.setRegion(region);
            dog.setName(dogName);
            dog.setNeedsEducated(isNeedsEducated);
            dog.setDescription(" ");
            dog.setAllerganics(" ");
        }
        dogRef.child(dog.getDogID()).setValue(dog, completionListener);
        DB.getReference("Dogs").child(dog.getDogID()).setValue(dog, completionListener);
        addPicture();
    }


    public void addPicture() {
        Intent intent = new Intent(AddDogActivity.this, addDogPicturesActivity.class);
        intent.putExtra("Dog", dog);
        startActivity(intent);
    }
}
