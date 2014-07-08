package com.habibiapp.habibi.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.habibiapp.habibi.MySQLHelper;

/**
 * Created by habibi on 6/12/14.
 */
public class GenderDataSource {
    public static final int MALE = 1;
    public static final int FEMALE = 2;


    // Database fields
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] allColumns = { MySQLHelper.COLUMN_ID, MySQLHelper.COLUMN_GENDER_NAME_INSERT};

    public GenderDataSource(Context context) {
        dbHelper = new MySQLHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createGender(String genderName) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_GENDER_NAME, genderName);
        try {
            long insertId = database.insert(MySQLHelper.TABLE_GENDER, null, values);
            Log.v("Gender", genderName + " ID: " + Integer.toString((int)insertId));
        } catch (Exception e) {
            Log.e("SQL", "Gender: Error inserting " + values, e);
        }
    }
}
