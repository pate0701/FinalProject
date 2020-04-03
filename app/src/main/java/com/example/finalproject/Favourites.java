package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    ListView list;
    ArrayList<Coordinates> coordinatesList = new ArrayList<> ();
    ListAdapter adapter;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_favourites);

        list = findViewById (R.id.coordinateList);
        loadDataFromDatabase ();

        adapter = new ListAdapter ();
        list.setAdapter (adapter);

    }

    private void loadDataFromDatabase()
    {
        DatabaseOpener dbOpener = new DatabaseOpener (this);
        db = dbOpener.getWritableDatabase();


        String [] columns = {DatabaseOpener.COL_ID, DatabaseOpener.COL_LATITUDE, DatabaseOpener.COL_LONGITUDE};
        Cursor results = db.query(false, DatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int latitudeColumnIndex = results.getColumnIndex(DatabaseOpener.COL_LATITUDE);
        int longitudeColIndex = results.getColumnIndex(DatabaseOpener.COL_LONGITUDE);
        int idColIndex = results.getColumnIndex(DatabaseOpener.COL_ID);

        while(results.moveToNext())
        {
            double latitude = results.getDouble (latitudeColumnIndex);
            double longitude = results.getDouble(longitudeColIndex);
            long id = results.getLong(idColIndex);

            coordinatesList.add(new Coordinates (latitude, longitude, id));

            list.setOnItemClickListener (new AdapterView.OnItemClickListener () {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showImage (position);
                }
            });
        }

    }

    protected void showImage(int position)
    {
        Coordinates selectedCoordinate = coordinatesList.get(position);

        View image_view = getLayoutInflater().inflate(R.layout.view_favourite, null);
        ImageView image = image_view.findViewById(R.id.viewImage);
        try {
            ContextWrapper cw = new ContextWrapper (getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            String fileName= selectedCoordinate.getId () +".jpg";
            File f=new File(directory, fileName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream (f));
            image.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Requested image")
                .setMessage("You can delete favourite by clicking Delete.")
                .setView(image_view)
                .setNegativeButton("Delete", (click, b) -> {
                    deleteCoordinate(selectedCoordinate); //remove the contact from database
                    coordinatesList.remove(position); //remove the contact from contact list
                    adapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> { })
                .create().show();

    }

    protected void deleteCoordinate(Coordinates c)
    {
        db.delete(DatabaseOpener.TABLE_NAME, DatabaseOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
        ContextWrapper cw = new ContextWrapper (getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String fileName= c.getId () +".jpg";
        File f=new File(directory, fileName);
        f.delete ();
    }

    protected class ListAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return coordinatesList.size();
        }

        public Coordinates getItem(int position){
            return coordinatesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = getLayoutInflater().inflate(R.layout.coordinate_list_item, parent, false );

            Coordinates thisRow = getItem(position);

            //get the TextViews
            TextView itemLatitue = (TextView)newView.findViewById(R.id.listitem_latitude);
            TextView itemLongitude = (TextView)newView.findViewById(R.id.listitem_longitude);

            //update the text fields:
            itemLatitue.setText(  String.valueOf (thisRow.getLatitude ()));
            itemLongitude.setText(String.valueOf (thisRow.getLongitude ()));

            //return the row:
            return newView;
        }

    }
}
