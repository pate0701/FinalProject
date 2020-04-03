package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


     /*   Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    ImageMainActivity.class);
            startActivity(intent);
        });*/

        ImageButton nasaImgView = findViewById(R.id.button2);
        nasaImgView.setOnClickListener(e -> {
            Intent i = new Intent(this, ImageMainActivity.class);
            startActivity(i);
        });



    }
}
