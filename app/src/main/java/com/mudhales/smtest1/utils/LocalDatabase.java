package com.mudhales.smtest1.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mudhales.smtest1.data.CountryDescription;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabase extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SMTest1.db";
    private static final String TABLE_RECORD_LIST = "recordlist";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_HREF = "image_href";
    private static final String COLUMN_NAME = "name";

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String incidentTable = "CREATE TABLE " + TABLE_RECORD_LIST + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_IMAGE_HREF + " TEXT," +
                COLUMN_NAME + " TEXT)";

        db.execSQL(incidentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD_LIST);
        onCreate(db);

    }


    // Adding new records
    public synchronized long addRecords(String name, List<CountryDescription> list) {
        deleteRecords();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = 0;
        for (CountryDescription data : list) {
            values.put(COLUMN_TITLE, data.getTitle()); //
            values.put(COLUMN_DESCRIPTION, data.getDescription()); //
            values.put(COLUMN_IMAGE_HREF, data.getImageHref()); //
            values.put(COLUMN_NAME, name);
            // Inserting Row
            id = db.insert(TABLE_RECORD_LIST, null, values);
        }
        db.close(); // Closing database connection
        return id;
    }


    // Getting All records
    public synchronized ArrayList<CountryDescription> getAllRecords() {
        ArrayList<CountryDescription> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RECORD_LIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CountryDescription data = new CountryDescription();
                    data.setTitle(cursor.getString(1));
                    data.setDescription(cursor.getString(2));
                    data.setImageHref(cursor.getString(3));
                    // Adding records to list
                    list.add(data);
                } while (cursor.moveToNext());
            }
            // return records list
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
            return list;
        } catch (Exception e) {
          //  Log.e("all_records", "" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return list;
    }

    public synchronized void deleteRecords() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_RECORD_LIST, null,
                    null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
