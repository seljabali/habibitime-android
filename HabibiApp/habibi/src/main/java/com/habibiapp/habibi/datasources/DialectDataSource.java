package com.habibiapp.habibi.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.habibiapp.habibi.MySQLHelper;
import com.habibiapp.habibi.models.Dialect;

/**
 * Created by habibi on 6/12/14.
 */
public class DialectDataSource {
    public static final int JORDAN = 0;

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
            database.insert(MySQLHelper.TABLE_DIALECT, null, values);
        } catch (Exception e) {
            Log.e("SQL", "Dialect: Error inserting " + values, e);
        }
    }
}
