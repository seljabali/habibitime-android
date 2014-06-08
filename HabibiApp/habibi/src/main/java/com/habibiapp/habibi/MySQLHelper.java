package com.habibiapp.habibi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table ";
    public static final String COLUMN_ID = "_id ";
    public static final String COLUMN_ID_INSERT = "_id integer primary key autoincrement, ";

    public static final String TABLE_HABIBI_PHRASE = "habibi_phrase";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_POPULARITY_INSERT = "popularity integer";

    public static final String TABLE_PHRASE = "phrase";
    public static final String COLUMN_LANGUAGE = "language integer, ";
    public static final String COLUMN_DIALECT = "dialect integer, ";
    public static final String COLUMN_FROM_GENDER = "from_gender integer, ";
    public static final String COLUMN_TO_GENDER = "to_gender integer, ";
    public static final String COLUMN_NATIVE_PHRASE_STRING = "native_phrase text, ";
    public static final String COLUMN_PHONETIC_SPELLING = "phonetic_spelling text, ";
    public static final String COLUMN_PROPER_PHONTETIC_SPELLING = "proper_phonetic_spelling text";


    private static final String DATABASE_NAME = "habibi_phrases.db";
    private static final int DATABASE_VERSION = 1;

    // create table habibi_phrase ( _id integer primary key autoincrement, popularity);"
    private static final String DATABASE_CREATE =
            CREATE_TABLE + TABLE_HABIBI_PHRASE
            + "(" + COLUMN_ID_INSERT
            +       COLUMN_POPULARITY_INSERT + " ); ";

//            + CREATE_TABLE + TABLE_PHRASE
//            + "(" + COLUMN_ID_INSERT
//            +       COLUMN_LANGUAGE
//            +       COLUMN_DIALECT
//            +       COLUMN_FROM_GENDER
//            +       COLUMN_TO_GENDER
//            +       COLUMN_NATIVE_PHRASE_STRING
//            +       COLUMN_PHONETIC_SPELLING
//            +       COLUMN_PROPER_PHONTETIC_SPELLING + ");"
//            ;

    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABIBI_PHRASE);
        onCreate(db);
    }

} 