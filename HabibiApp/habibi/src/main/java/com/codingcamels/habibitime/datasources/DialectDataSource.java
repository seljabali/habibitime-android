package com.codingcamels.habibitime.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codingcamels.habibitime.MySQLHelper;

/**
 * Created by habibi on 6/12/14.
 */
public class DialectDataSource {
    public static final int JORDAN = 1;

    // Database fields
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] allColumns = { MySQLHelper.COLUMN_ID, MySQLHelper.COLUMN_DIALECT_INSERT};

    public DialectDataSource(Context context) {
        dbHelper = new MySQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createDialect(String dialectName) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_DIALECT_NAME, dialectName);
        try {
            long insertId = database.insert(MySQLHelper.TABLE_DIALECT, null, values);
            Log.v("Dialect", dialectName + " ID: " + Integer.toString((int)insertId));
        } catch (Exception e) {
            Log.e("SQL", "Dialect: Error inserting " + values, e);
        }
    }
}
