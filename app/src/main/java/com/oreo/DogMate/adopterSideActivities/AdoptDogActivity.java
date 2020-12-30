package com.oreo.DogMate.adopterSideActivities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oreo.DogMate.Navigation.Adopter_Navigation;
import com.oreo.DogMate.Objects.Adopter;
import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Activity for filling detail for the adoption after choosing a dog to adoption
 */
public class AdoptDogActivity extends Adopter_Navigation {

    Button Adopt;
    EditText editTextInfo;
    String dateS, info;
    FirebaseDatabase DB;
    FirebaseAuth auth;
    DatabaseReference adoptionsAdoRef;
    DatabaseReference adoptionsAdvRef;
    DatabaseReference adopterRef;
    String userID;
    Advertiser advertiser;
    Dog dog;
    Adopter adopter;
    Adoption adoption;
    String orderNum;

    int year, month, day;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener mDateListener;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_dog);
        calendar = Calendar.getInstance();
        Intent intent = getIntent();
        dog = (Dog) intent.getSerializableExtra("Dog");
        advertiser = (Advertiser) intent.getSerializableExtra("Advertiser");
        Adopt = findViewById(R.id.adopt);
        TextView suggestion = findViewById(R.id.suggestion);
        editTextInfo = findViewById(R.id.editText);
        suggestion.setText("מעוניינים ב- " + dog.getName() + "?");
        suggestion.append("\n ספרו קצת על עצמכם: \n *רקע כללי,איפה אתם גרים,מי גר בבית, במה עוסקים?\n * האם גרים בדירה או בית פרטי? \n *האם גידלתם כלבים/גורים בעבר? \n *מי יהיה אחראי על טיולים לכלב? \n *האם יש זמן לפעילות גופנים יומיומית עם הכלב? \n *כמה זמן ביום ואיפה תערך פעילות זו?\n");
        suggestion.append("מאחלים לכם אימוץ נעים:)");
        DB = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        adoptionsAdvRef = DB.getReference("Adoptions/Advertiser Adoptions");
        adoptionsAdoRef = DB.getReference("Adoptions/Adopter Adoptions");
        adopterRef = DB.getReference("Users").child("Adopter").child(userID);
        //adding a date chooser


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                day = Day;
                month = Month + 1;
                year = Year;

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        //getting the adopter info
        adopterRef = DB.getReference("Users/Adopter");
        adopterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                if (dataSnapshot.hasChild(userID)) {
                    adopter = dataSnapshot.child(userID).getValue(Adopter.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void CreateNewOrderC(View view) {
        findViewById(R.id.adopt).setEnabled(true);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date2 = new Date();
        dateS = dateFormat.format(date2);
        // dateS = (day + "/" + month + "/" + year);
        info = editTextInfo.getText().toString().trim();


        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            }
        };
        orderNum = adoptionsAdvRef.push().getKey();
        adoption = new Adoption(adopter, advertiser, dog,
                dateS, info);
        adoptionsAdoRef.child(adopter.getUserID()).child(orderNum).setValue(adoption, completionListener);
        adoptionsAdvRef.child(advertiser.getUserID()).child(orderNum).setValue(adoption, completionListener);

        // when finishing to adoption - moves to the orders list
        Toast.makeText(AdoptDogActivity.this, "בקשת האימוץ בוצע בהצלחה!", Toast.LENGTH_LONG).show();
        findViewById(R.id.adopt).setEnabled(true);
        startActivity(new Intent(AdoptDogActivity.this, AdopterOrderActivity.class));
    }

}
