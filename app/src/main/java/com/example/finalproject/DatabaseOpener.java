package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "NasaImagery";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "COORDINATES";
    public final static String COL_LATITUDE = "LATITUDE";
    public final static String COL_LONGITUDE = "LONGITUDE";
    public final static String COL_ID = "_id";

    public DatabaseOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_LATITUDE + " real,"
                + COL_LONGITUDE  + " real);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
