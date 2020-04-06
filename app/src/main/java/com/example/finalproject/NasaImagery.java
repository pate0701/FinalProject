package com.example.finalproject;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NasaImagery extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button fetch,add_fav;
    EditText mlatitude,mlongitude;
    ImageView imgResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_nasa_imagery);
        Toolbar tBar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this::onNavigationItemSelected);

        fetch = findViewById (R.id.btn_fetch);
        mlatitude = findViewById (R.id.tv_latitude);
        mlongitude = findViewById (R.id.tv_longitute);
        imgResult = findViewById (R.id.img_result);
        add_fav = findViewById (R.id.btn_add_fav);

        fetch.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                double latitude = Double.parseDouble (mlatitude.getText ().toString ());
                double longitude = Double.parseDouble (mlongitude.getText ().toString ());

                ProgressDialog progressDialog = new ProgressDialog (NasaImagery.this);
                progressDialog.setMessage ("Loading Image...");
                progressDialog.show ();

                new FetchImage (imgResult,latitude,longitude, progressDialog).execute ();
            }
        });

        DatabaseOpener dbOpener = new DatabaseOpener (this);
        SQLiteDatabase db = dbOpener.getWritableDatabase ();

        add_fav.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                double latitude = Double.parseDouble (mlatitude.getText ().toString ());
                double longitude = Double.parseDouble (mlongitude.getText ().toString ());

                ContentValues newRowValues = new ContentValues();

                newRowValues.put(DatabaseOpener.COL_LATITUDE, latitude);
                newRowValues.put(DatabaseOpener.COL_LONGITUDE, longitude);


                long newId = db.insert(DatabaseOpener.TABLE_NAME, null, newRowValues);
                BitmapDrawable drawable = (BitmapDrawable) imgResult.getDrawable ();
                Bitmap img = drawable.getBitmap ();
                saveToInternalStorage (img,newId);

                Toast.makeText(NasaImagery.this,"Added to Favourites",Toast.LENGTH_LONG).show ();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.itemNav1:
                startActivity(new Intent(NasaImagery.this, NasaImagery.class));
                Toast.makeText(this, "Enter Latitude and Longitude and Click Fetch Button.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemNav2:
                startActivity(new Intent(NasaImagery.this, Favourites.class));
                Toast.makeText(this, "Click On Any Favorites to View and Delete.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemNav3:
                finish();
                break;
        }

        return true;
    }

    private void saveToInternalStorage(Bitmap bitmapImage,long id){
        ContextWrapper cw = new ContextWrapper (getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String fileName = id +".jpg";
        File mypath=new File(directory,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nasa_imagery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.show_fav: {
                startActivity(new Intent(NasaImagery.this, Favourites.class));
            } break;

            case R.id.help: {
                startActivity(new Intent(NasaImagery.this, About.class));
            }

        }
        return true;
    }
}
