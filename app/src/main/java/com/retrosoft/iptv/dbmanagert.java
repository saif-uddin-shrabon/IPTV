package com.retrosoft.iptv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbmanagert extends SQLiteOpenHelper {

    private static final String dbname = "channeList.db";
    private static final String dbtable="ListOfChannel";


    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_LINK="url";

    private Context context;

    public dbmanagert(@Nullable Context context) {
        super(context, dbname, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + dbtable + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_LINK + " TEXT);";

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

    public boolean addRecord(String name, String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LINK, link);
        long result = db.insert(dbtable, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to insert record", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Record inserted successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + dbtable + " ORDER BY " + COLUMN_ID + " ASC";
        return db.rawQuery(query, null);
    }


//    public void deleteAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(dbtable, null, null);
//        Toast.makeText(context, "All data deleted successfully", Toast.LENGTH_SHORT).show();
//    }
    public boolean deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(dbtable, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (result == 0) {
            Toast.makeText(context, "Failed to delete record", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Record deleted successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    /*
    public dbmanagert(@Nullable Context context) {
        super(context, dbname, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to create any tables here as they will be created dynamically
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need to upgrade any tables here as they will be created dynamically
    }

    public boolean addRecord(String tableName, String name, String logo, String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOGO, logo);
        cv.put(COLUMN_LINK, link);


        String tablename = "table_" + tableName.replace("http://", "").replace("https://", "").replace(".", "_").replace("-", "_").replace("/", "_");
        System.out.println("Table_Name: "+tablename);

        String query = "CREATE TABLE " + tablename + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_LOGO + " TEXT,"
                + COLUMN_LINK + " TEXT);";

        try {
            db.execSQL(query);
            long result = db.insert(tableName, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed to insert record", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Toast.makeText(context, "Record inserted successfully", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Channel", "Name: " + e.getMessage());
            return false;
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        StringBuilder sb = new StringBuilder();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                sb.append(cursor.getString(0)).append(",");
                cursor.moveToNext();
            }
        }
        cursor.close();
        String tableNames = sb.toString();
        if (tableNames.length() > 0) {
            tableNames = tableNames.substring(0, tableNames.length() - 1); // Remove last comma
        }

        return db.rawQuery("SELECT * FROM " + tableNames, null);
    }

    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Toast.makeText(context, "Table " + tableName + " deleted successfully", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        StringBuilder sb = new StringBuilder();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                sb.append(cursor.getString(0)).append(",");
                cursor.moveToNext();
            }
        }
        cursor.close();
        String tableNames = sb.toString();
        if (tableNames.length() > 0) {
            tableNames = tableNames.substring(0, tableNames.length() - 1); // Remove last comma
        }

        db.execSQL("DROP TABLE IF EXISTS " + tableNames);
        Toast.makeText(context, "All data deleted successfully", Toast.LENGTH_SHORT).show();
    }

    public List<String> getAllTableNames() {
        List<String> tableNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (cursor.moveToFirst()) {
            do {
                tableNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tableNames;
    }


     */

}

