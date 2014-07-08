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
public class LanguageDataSource {
    public static final int ARABIC = 1;
    public static final int ENGLISH = 2;


    // Database fields
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] allColumns = { MySQLHelper.COLUMN_ID, MySQLHelper.COLUMN_LANGUAGE_NAME_INSERT };

    public LanguageDataSource(Context context) {
        dbHelper = new MySQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createLanguage(String languageName) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_LANGUAGE_NAME, languageName);
        try {
            long insertId = database.insert(MySQLHelper.TABLE_LANGUAGE, null, values);
            Log.v("LanguageName", languageName + " ID: " + Integer.toString((int)insertId));
        } catch (Exception e) {
            Log.e("SQL", "Language: Error inserting " + values, e);
        }
    }
}
