package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imageview;
    SharedPreferences sharedPreferences;
    String jsonData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview = (ImageView)findViewById(R.id.BBCNEWS);
        imageview.setOnClickListener((v) -> {
            startActivity(new Intent(this,NewsDownloader.class));
        });
        sharedPreferences = getSharedPreferences("App_settings", MODE_PRIVATE);
        // add values for your ArrayList any where...
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DATE_LIST", jsonData);
        editor.apply();




    }
}
