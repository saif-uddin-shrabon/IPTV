package com.retrosoft.iptv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

public class dbmanager extends SQLiteOpenHelper {

    private static final String dbname="channelList.db";
    private static final String dbtable="ChannelList";


    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_LOGO="logo";
    private static final String COLUMN_LINK="url";
    private static final String COLUMN_fvrt="favourit";
    private static final String COLUMN_ROOT_LINK = "RootLink"; // New column
    private Context context;

    public dbmanager(@Nullable Context context) {
        super(context, dbname, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + dbtable + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_LOGO + " TEXT,"
                + COLUMN_LINK + " TEXT,"
                + COLUMN_fvrt + " TEXT DEFAULT 'f'," // Existing column
                + COLUMN_ROOT_LINK + " TEXT" // New column
                + ");";

        try {
            db.execSQL(query);
            Toast.makeText(context, "Table created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS " + dbtable;
        try {
            db.execSQL(query);
            onCreate(db);
            Toast.makeText(context, "Table upgraded successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean addRecord(String name, String logo, String link, String rootLink) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOGO, logo);
        cv.put(COLUMN_LINK, link);
        cv.put(COLUMN_fvrt, "f");
        cv.put(COLUMN_ROOT_LINK, rootLink); // New column value
        long result = db.insert(dbtable, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to insert record", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // Toast.makeText(context, "Record inserted successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + dbtable + " ORDER BY " + COLUMN_ID + " ASC";
        return db.rawQuery(query, null);
    }

    public Cursor readAllFavData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + dbtable + " WHERE " + COLUMN_fvrt + "='t'";
        return db.rawQuery(query, null);
    }



    public boolean updateFavoriteStatus(int isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_fvrt, "t");
        int rowsAffected = db.update(dbtable, cv, COLUMN_ID + "=?",  new String[]{String.valueOf(isFavorite)});
        if (rowsAffected > 0) {
            Toast.makeText(context, "Record updated successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Failed to update record", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean RemoveFavorite(int isNotFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_fvrt, "f");
        int rowsAffected = db.update(dbtable, cv, COLUMN_ID + "=?",  new String[]{String.valueOf(isNotFavorite)});
        if (rowsAffected > 0) {
            Toast.makeText(context, "Record updated successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Failed to update record", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void deleteAllData(String  linkdelete) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbtable, COLUMN_ROOT_LINK + "=?", new String[]{String.valueOf(linkdelete)});
        Toast.makeText(context, "All data deleted successfully", Toast.LENGTH_SHORT).show();
    }


}
