package com.example.finalproject;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;

public class MainActivityGuardianJava extends AppCompatActivity {


    private DrawerLayout drawerLayout;

    private SearchNewsFragmentJava searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_main);

        Toolbar toolbar = findViewById(R.id.toolbarId);
        toolbar.setTitle(R.string.guardian_button);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setCheckedItem(R.id.drawerMenuSearch);
        searchFragment = new SearchNewsFragmentJava();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                searchFragment).commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawerMenuSearch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                searchFragment).commit();
                        break;
                    case R.id.drawerMenuSaved:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                new SavedNewsFragmentJava()).commit();
                        break;
                    case R.id.BBCNews:
                        Intent intent3 = new Intent(MainActivityGuardianJava.this, BBC.class);
                        startActivity(intent3);
                        break;
                    case R.id.NasaImage:
                        Intent intent4 = new Intent(MainActivityGuardianJava.this, NasaImages.class);
                        startActivity(intent4);
                        break;
                    case R.id.NasaEarth:
                        Intent intent5 = new Intent(MainActivityGuardianJava.this, NASAEARTH.class);
                        startActivity(intent5);
                        break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guardian_toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.NasaImage:
                Intent intent1 = new Intent(this, NasaImages.class);
                startActivity(intent1);
                return true;
            case R.id.NasaEarth:
                Intent intent2 = new Intent(this, NASAEARTH.class);
                startActivity(intent2);
                return true;
            case R.id.BBCNews:
                Intent intent3 = new Intent(this, BBC.class);
                startActivity(intent3);
                return true;
            case R.id.menuHelp:
                showHelpDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void showHelpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.help));
        builder.setMessage(getString(R.string.article_help));
        builder.setPositiveButton(getString(R.string.article_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}