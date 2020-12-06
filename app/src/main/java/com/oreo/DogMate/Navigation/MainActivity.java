package com.oreo.DogMate.Navigation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oreo.DogMate.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main activity - the first activity the users sees when he open's the app
 */
public class MainActivity extends AppCompatActivity {
    Button newUser;
    Button Exsitadopter;
    TextView welcome;
    Typeface font;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "firebase conection success",Toast.LENGTH_LONG).show();
        newUser = (Button) findViewById(R.id.adopter);
        Exsitadopter = (Button) findViewById(R.id.Baker);
        welcome = (TextView) findViewById(R.id.Hello);
        font = Typeface.createFromAsset(this.getAssets(), "fonts/Anka CLM Bold.ttf");
        newUser.setTypeface(font);
        Exsitadopter.setTypeface(font);
        welcome.setTypeface(font);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
           }
        });
        Exsitadopter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });



    }
}
