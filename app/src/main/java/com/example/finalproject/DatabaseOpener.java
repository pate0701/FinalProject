package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "SavedNews";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "FAVOURITE";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_DESCRIPTION = "DESCRIPTION";
    public final static String COL_ID = "_id";
    public final static String COL_URL = "URL";

    public DatabaseOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT,"
                + COL_DESCRIPTION + " TEXT,"
                + COL_URL  + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
