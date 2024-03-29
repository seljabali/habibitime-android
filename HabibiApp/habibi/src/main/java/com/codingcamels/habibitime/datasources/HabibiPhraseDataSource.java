package com.codingcamels.habibitime.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codingcamels.habibitime.MySQLHelper;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.HabibiPhrase;
import com.codingcamels.habibitime.models.Phrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsoom on 6/8/14.
 */
public class HabibiPhraseDataSource {

    private Context context;
    // Database fields
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] allColumns = { MySQLHelper.COLUMN_ID,
                                    MySQLHelper.COLUMN_CATEGORY};

    public HabibiPhraseDataSource(Context context) {
        this.context = context;
        dbHelper = new MySQLHelper(context);
    }

    public HabibiPhraseDataSource(MySQLHelper mySQLHelper) {
        dbHelper = mySQLHelper;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createHabibiPhrase(Category category) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_CATEGORY, category.getId());

        long id = -1;
        try {
            id = database.insert(MySQLHelper.TABLE_HABIBI_PHRASE, null, values);
            if (id == -1) {
                Log.e("Add Habibi Phrase", "Couldn't add phrase for category: " + category.toString());
            }
        } catch (Exception e) {
            Log.e("Add Habibi Phrase", "Habibi Phrase: Error inserting " + values, e);
        }
        return id;
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

    public long createHabibiPhrase(Category category, List<Phrase> phrases) {
        long id = createHabibiPhrase(category);
        if (id != -1) {
            PhraseDataSource phraseDataSource = new PhraseDataSource(context);
            phraseDataSource.open();
            for (Phrase phrase : phrases) {
                phraseDataSource.createPhrase(id, phrase);
            }
            phraseDataSource.close();
        }
        return id;
    }

    private HabibiPhrase cursorToHabibiPhrase(Cursor cursor) {
        HabibiPhrase habibiPhrase = new HabibiPhrase();
        habibiPhrase.setId(cursor.getLong(0));
        habibiPhrase.setCategory(cursor.getInt(1));
        return habibiPhrase;
    }
}