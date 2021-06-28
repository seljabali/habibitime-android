package com.codingcamels.habibitime.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codingcamels.habibitime.MySQLHelper;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Dialect;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;
import com.codingcamels.habibitime.utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by habibi on 6/12/14.
 */
public class PhraseDataSource {
    public static final String TAG = PhraseDataSource.class.getSimpleName();
    public static final String NA = "na";
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
    public static final int  COLUMN_SOUND_FILE_LOCATION = 9;
    private String[] allColumns = { MySQLHelper.COLUMN_ID,
                                    MySQLHelper.COLUMN_HABIBI_PHRASE,
                                    MySQLHelper.COLUMN_LANGUAGE,
                                    MySQLHelper.COLUMN_DIALECT,
                                    MySQLHelper.COLUMN_FROM_GENDER,
                                    MySQLHelper.COLUMN_TO_GENDER,
                                    MySQLHelper.COLUMN_NATIVE_PHRASE_STRING,
                                    MySQLHelper.COLUMN_PHONETIC_SPELLING,
                                    MySQLHelper.COLUMN_PROPER_PHONTETIC_SPELLING,
                                    MySQLHelper.COLUMN_SOUND_FILE_LOCATION};

    public PhraseDataSource(Context context) {
        dbHelper = new MySQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPhrase(long habibiPhraseId, Phrase phrase) {
        createPhrase(habibiPhraseId, phrase.getLanguage().getId(), -1,
                phrase.getFromGender().getId(), phrase.getToGender().getId(), phrase.getPhoneticPhraseSpelling(),
                phrase.getProperPhoneticPhraseSpelling(), phrase.getSoundFileName());
    }

    public void createPhrase(long habibiPhraseId, int language, int dialect, int from_gender,
                             int to_gender, String nativeString, String phoneticString,
                             String properString) {
        createPhrase(habibiPhraseId, language, dialect, from_gender, to_gender, nativeString, phoneticString, properString, "");
    }

    public void createPhrase(long habibiPhraseId, Language language, Dialect dialect, Gender fromGender,
                             Gender toGender, String nativeString, String phoneticString,
                             String properString, String fileLocation) {
        createPhrase(habibiPhraseId, language.getId(), dialect.getId(), fromGender.getId(),
                toGender.getId(), nativeString, phoneticString, properString, fileLocation);
    }

    public void createPhrase(long habibiPhraseId, int language, int dialect, int from_gender,
                               int to_gender, String nativeString, String phoneticString,
                               String properString, String fileLocation) {
        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_HABIBI_PHRASE, habibiPhraseId);
        values.put(MySQLHelper.COLUMN_LANGUAGE, language);
        values.put(MySQLHelper.COLUMN_DIALECT, dialect);
        values.put(MySQLHelper.COLUMN_FROM_GENDER, from_gender);
        values.put(MySQLHelper.COLUMN_TO_GENDER, to_gender);
        values.put(MySQLHelper.COLUMN_NATIVE_PHRASE_STRING, nativeString);
        values.put(MySQLHelper.COLUMN_PHONETIC_SPELLING, phoneticString);
        values.put(MySQLHelper.COLUMN_PROPER_PHONTETIC_SPELLING ,properString);
        values.put(MySQLHelper.COLUMN_SOUND_FILE_LOCATION, fileLocation);

        long result = -1;
        try {
            result = database.insert(MySQLHelper.TABLE_PHRASE, null, values);
            if (result == -1) {
                Log.e("Inserting into Phrase: ", "Result: " + result + " habibiId: " + habibiPhraseId + " Language: " + language +
                        " Dialect: " + dialect + " from_gender: " + from_gender);
            }
        } catch (Exception e) {
            Log.e("Inserting into Phrase: ", "Result: " + result + " habibiId: " + habibiPhraseId + " Language: " + language +
                    " Dialect: " + dialect + " from_gender: " + from_gender, e);
        }
    }

    public List<Phrase> getPhrases(int habibiPhraseId, Category category, Gender fromGender, Gender toGender, Language fromLanguage, Dialect fromDialect) {
        String rawQuery = " SELECT * "
                        + " FROM " + MySQLHelper.TABLE_PHRASE + " AS P"
                        + " INNER JOIN " + MySQLHelper.TABLE_HABIBI_PHRASE + " AS H"
                        + " ON  P." + MySQLHelper.COLUMN_HABIBI_PHRASE + " = H." + MySQLHelper.COLUMN_ID
                        + " WHERE ";
        boolean needsAND = false;
        if (category != null) {
            needsAND = true;
            rawQuery += " H." + MySQLHelper.COLUMN_CATEGORY + " = " + Integer.toString(category.getId());
        }
        if (fromGender != null) {
            if (needsAND) {
                rawQuery += " AND ";
            }
            rawQuery += " P." + MySQLHelper.COLUMN_FROM_GENDER + " = " + Integer.toString(fromGender.getId());
            needsAND = true;
        }
        if (toGender != null) {
            if (needsAND) {
                rawQuery += " AND ";
            }
            rawQuery += " P." + MySQLHelper.COLUMN_TO_GENDER + " = " + Integer.toString(toGender.getId());
            needsAND = true;
        }
        if (fromLanguage != null) {
            if (needsAND) {
                rawQuery += " AND ";
            }
            rawQuery += " P." + MySQLHelper.COLUMN_LANGUAGE + " = " + Integer.toString(fromLanguage.getId());
            needsAND = true;
        }
        if (habibiPhraseId != -1) {
            if (needsAND) {
                rawQuery += " AND ";
            }
            rawQuery += " H." + MySQLHelper.COLUMN_ID + " = " + habibiPhraseId;
        }
        rawQuery += ";";

        Cursor cursor = null;
        List<Phrase> phrases = null;
        try {
            cursor = database.rawQuery(rawQuery, null);
            cursor.moveToFirst();
            phrases = new ArrayList<Phrase>();
            while (!cursor.isAfterLast()) {
                Phrase habibiPhrase = cursorToPhrase(cursor);
                phrases.add(habibiPhrase);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("Getting Phrases: ", e.toString());
            if (cursor != null) {
                cursor.close();
            }

        }
        return phrases;
    }

    public void deletePhrase(Phrase phrase) {
        long id = phrase.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLHelper.TABLE_HABIBI_PHRASE, MySQLHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Phrase> getAllPhrases() {
        List<Phrase> phrases = new ArrayList<Phrase>();

        Cursor cursor = database.query(MySQLHelper.TABLE_PHRASE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Phrase habibiPhrase = cursorToPhrase(cursor);
            phrases.add(habibiPhrase);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return phrases;
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
        phrase.setSoundFileName(cursor.getString(COLUMN_SOUND_FILE_LOCATION));
        return phrase;
    }


    public static String getPhraseSoundFileName(String englishText, Language language, Gender fromGender, Gender toGender) {
        if (StringUtils.isEmpty(englishText)) {
            Log.e(PhraseDataSource.TAG, "Missing englishText to get sound file name.");
            return "";
        }
        return getPhraseSoundFileName(englishText, language.getLanguageName(), fromGender.getGenderNameShortened(), toGender.getGenderNameShortened());
    }
    private static String getPhraseSoundFileName(String englishText, String language, String fromGender, String toGender) {
        String fileName = englishText.replaceAll("[^a-zA-Z\\_\\ ]", "");
        fileName = fileName.replace(" ", "_").toLowerCase().trim();
        if (StringUtils.isNotEmpty(language)) {
            fileName += "_" + language.toLowerCase();
        }
        fromGender = StringUtils.isEmpty(fromGender) ? "" : fromGender.toLowerCase();
        fileName += "_" + StringUtils.getBIfAEmpty(fromGender, NA);

        toGender = StringUtils.isEmpty(toGender) ? "" : toGender.toLowerCase();
        fileName += "_" + StringUtils.getBIfAEmpty(toGender, NA);
        return fileName.trim();
    }

}