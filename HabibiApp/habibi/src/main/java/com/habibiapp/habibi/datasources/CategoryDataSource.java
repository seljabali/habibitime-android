package com.habibiapp.habibi.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.habibiapp.habibi.MySQLHelper;
import com.habibiapp.habibi.models.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by habibi on 6/13/14.
 */
public class CategoryDataSource {
    public static final int MOOD = 0;
    public static final int QUESTION = 1;
    public static final int ANSWER = 2;
    public static final int FLIRT = 3;

    // Database fields
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] allColumns = { MySQLHelper.COLUMN_ID, MySQLHelper.COLUMN_CATEGORY_NAME};

    public CategoryDataSource(Context context) {
        dbHelper = new MySQLHelper(context);
    }
    public CategoryDataSource(MySQLHelper mySQLHelper) {
        dbHelper = mySQLHelper;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createCategory(String categoryName) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_CATEGORY_NAME, categoryName);
        long insertId;
        try {
            insertId = database.insert(MySQLHelper.TABLE_CATEGORY, null, values);
        } catch (SQLException e) {
            Log.e("SQL", "Category: Error inserting " + values, e);
            return -1;
        }

        return insertId;
    }

    public List<Category> getCategories() {
        List<Category> categories = null;
        try {
            Cursor cursor = database.query(false, MySQLHelper.TABLE_CATEGORY, allColumns, null, null,
                    null, null, null, null);
            categories = cursorToCategory(cursor);
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQL", "Category: Error getting Categories " + e.toString());
        }
        return categories;
    }

    private List<Category> cursorToCategory(Cursor cursor) {
        List<Category> categories = new ArrayList<Category>();
        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setId((int)cursor.getLong(0));
            category.setCategoryName(cursor.getString(1));
            categories.add(category);
        }
        return categories;
    }

    public Cursor getAllCategories() {
        String[] tableColumns = new String[]{
                MySQLHelper.COLUMN_CATEGORY_NAME
        };
        return database.query(false, MySQLHelper.TABLE_CATEGORY, tableColumns, "*", null, null, null, null, null);
    }
}
