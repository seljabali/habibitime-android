package com.habibiapp.habibi.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.habibiapp.habibi.MySQLHelper;
import com.habibiapp.habibi.models.Phrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by habibi on 6/12/14.
 */
public class PhraseDataSource {
        // Database fields
        private SQLiteDatabase database;
        private MySQLHelper dbHelper;
        public static final int  COLUMN_ID = 0;
        public static final int  COLUMN_HABIBI_PHRASE_ID = 1;
        public static final int  COLUMN_LANGUAGE = 2;
        public static final int  COLUMN_DIALECT = 3;
        public static final int  COLUMN_FROM_GENDER = 4;
        public static final int  COLUMN_TO_GENDER = 5;
        public static final int  COLUMN_NATIVE_PHRASE_STRING = 6;
        public static final int  COLUMN_PHONETIC_SPELLING = 7;
        public static final int  COLUMN_PROPER_PHONTETIC_SPELLING = 8;
        private String[] allColumns = { MySQLHelper.COLUMN_ID,
                                        MySQLHelper.COLUMN_LANGUAGE,
                                        MySQLHelper.COLUMN_DIALECT,
                                        MySQLHelper.COLUMN_FROM_GENDER,
                                        MySQLHelper.COLUMN_TO_GENDER,
                                        MySQLHelper.COLUMN_NATIVE_PHRASE_STRING,
                                        MySQLHelper.COLUMN_PHONETIC_SPELLING,
                                        MySQLHelper.COLUMN_PROPER_PHONTETIC_SPELLING};

        public PhraseDataSource(Context context) {
            dbHelper = new MySQLHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public void createPhrase(long habibiPhraseId, int language, int dialect, int from_gender,
                                   int to_gender,String nativeString, String phoneticString,
                                   String properString) {

            ContentValues values = new ContentValues();
            values.put(MySQLHelper.COLUMN_HABIBI_PHRASE, habibiPhraseId);
            values.put(MySQLHelper.COLUMN_LANGUAGE, language);
            values.put(MySQLHelper.COLUMN_DIALECT, dialect);
            values.put(MySQLHelper.COLUMN_FROM_GENDER, from_gender);
            values.put(MySQLHelper.COLUMN_TO_GENDER, to_gender);
            values.put(MySQLHelper.COLUMN_NATIVE_PHRASE_STRING, nativeString);
            values.put(MySQLHelper.COLUMN_PHONETIC_SPELLING, phoneticString);
            values.put(MySQLHelper.COLUMN_PROPER_PHONTETIC_SPELLING ,properString);

            try {
                database.insert(MySQLHelper.TABLE_PHRASE, null, values);
            } catch (Exception e) {
                Log.e("SQL", "Phrase: Error inserting " + values, e);
            }
        }

        public void deletePhrase(Phrase phrase) {
            long id = phrase.getId();
            System.out.println("Comment deleted with id: " + id);
            database.delete(MySQLHelper.TABLE_HABIBI_PHRASE, MySQLHelper.COLUMN_ID
                    + " = " + id, null);
        }

        public List<Phrase> getAllPhrases() {
            List<Phrase> comments = new ArrayList<Phrase>();

            Cursor cursor = database.query(MySQLHelper.TABLE_HABIBI_PHRASE,
                    allColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Phrase habibiPhrase = cursorToPhrase(cursor);
                comments.add(habibiPhrase);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return comments;
        }

        private Phrase cursorToPhrase(Cursor cursor) {
            Phrase phrase = new Phrase();
            phrase.setId(cursor.getInt(COLUMN_ID));
            phrase.setHabibiPhraseId(cursor.getInt(COLUMN_HABIBI_PHRASE_ID));
            phrase.setLanguage(cursor.getInt(COLUMN_LANGUAGE));
            phrase.setDialect(cursor.getInt(COLUMN_DIALECT));
            phrase.setFromGender(cursor.getInt(COLUMN_FROM_GENDER));
            phrase.setToGender(cursor.getInt(COLUMN_TO_GENDER));
            phrase.setNativePhraseSpelling(cursor.getString(COLUMN_NATIVE_PHRASE_STRING));
            phrase.setPhoneticPhraseSpelling(cursor.getString(COLUMN_PHONETIC_SPELLING));
            phrase.setProperPhoneticPhraseSpelling(cursor.getString(COLUMN_PROPER_PHONTETIC_SPELLING));
            return phrase;
        }
}