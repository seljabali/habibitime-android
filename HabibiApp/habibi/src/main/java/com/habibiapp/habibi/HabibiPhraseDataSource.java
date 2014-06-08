package com.habibiapp.habibi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.habibiapp.habibi.models.HabibiPhrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsoom on 6/8/14.
 */
public class HabibiPhraseDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] allColumns = { MySQLHelper.COLUMN_ID,
            MySQLHelper.COLUMN_POPULARITY };

    public HabibiPhraseDataSource(Context context) {
        dbHelper = new MySQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public HabibiPhrase createHabibiPhrase(int popularity) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_POPULARITY, popularity);
        long insertId = database.insert(MySQLHelper.TABLE_HABIBI_PHRASE, null,
                values);
        Cursor cursor = database.query(MySQLHelper.TABLE_HABIBI_PHRASE,
                allColumns, MySQLHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        HabibiPhrase habibiPhrase;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            habibiPhrase = cursorToHabibiPhrase(cursor);
        } else {
            habibiPhrase = null;
        }
        cursor.close();
        return habibiPhrase;
    }

    public void deleteHabibiPhrase(HabibiPhrase habibiPhrase) {
        long id = habibiPhrase.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLHelper.TABLE_HABIBI_PHRASE, MySQLHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<HabibiPhrase> getAllHabibiPhrases() {
        List<HabibiPhrase> comments = new ArrayList<HabibiPhrase>();

        Cursor cursor = database.query(MySQLHelper.TABLE_HABIBI_PHRASE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HabibiPhrase habibiPhrase = cursorToHabibiPhrase(cursor);
            comments.add(habibiPhrase);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private HabibiPhrase cursorToHabibiPhrase(Cursor cursor) {
        HabibiPhrase habibiPhrase = new HabibiPhrase();
        habibiPhrase.setId(cursor.getLong(0));
        habibiPhrase.setPopularity(cursor.getInt(1));
        return habibiPhrase;
    }
}