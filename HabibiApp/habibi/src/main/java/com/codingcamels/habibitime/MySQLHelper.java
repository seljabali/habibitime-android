package com.codingcamels.habibitime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codingcamels.habibitime.datasources.CategoryDataSource;
import com.codingcamels.habibitime.datasources.DialectDataSource;
import com.codingcamels.habibitime.datasources.GenderDataSource;
import com.codingcamels.habibitime.datasources.HabibiPhraseDataSource;
import com.codingcamels.habibitime.datasources.LanguageDataSource;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Dialect;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MySQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habibi_phrases.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE = "create table ";
    public static final String COLUMN_ID = "_id ";
    public static final String COLUMN_ID_INSERT = "_id integer primary key autoincrement, ";

    //HABIBI PHRASE
    public static final String TABLE_HABIBI_PHRASE = "habibi_phrase";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_INSERT = "category text";

    //PHRASE
    public static final String TABLE_PHRASE = "phrase";
    public static final String COLUMN_HABIBI_PHRASE = "habibi_phrase_id";
    public static final String COLUMN_HABIBI_PHRASE_INSERT = "habibi_phrase_id integer, ";
    public static final String COLUMN_LANGUAGE = "language";
    public static final String COLUMN_LANGUAGE_INSERT = "language integer, ";
    public static final String COLUMN_DIALECT = "dialect";
    public static final String COLUMN_DIALECT_INSERT = "dialect integer, ";
    public static final String COLUMN_FROM_GENDER = "from_gender";
    public static final String COLUMN_FROM_GENDER_INSERT = "from_gender integer, ";
    public static final String COLUMN_TO_GENDER = "to_gender";
    public static final String COLUMN_TO_GENDER_INSERT = "to_gender integer, ";
    public static final String COLUMN_NATIVE_PHRASE_STRING = "native_phrase";
    public static final String COLUMN_NATIVE_PHRASE_STRING_INSERT = "native_phrase text, ";
    public static final String COLUMN_PHONETIC_SPELLING = "phonetic_spelling";
    public static final String COLUMN_PHONETIC_SPELLING_INSERT = "phonetic_spelling text, ";
    public static final String COLUMN_PROPER_PHONTETIC_SPELLING = "proper_phonetic_spelling";
    public static final String COLUMN_PROPER_PHONTETIC_SPELLING_INSERT = "proper_phonetic_spelling text";

    //LANGUAGE
    public static final String TABLE_LANGUAGE = "language";
    public static final String COLUMN_LANGUAGE_NAME = "language_name";
    public static final String COLUMN_LANGUAGE_NAME_INSERT = "language_name text";

    //DIALECTs
    public static final String TABLE_DIALECT = "dialect";
    public static final String COLUMN_DIALECT_NAME = "dialect_name";
    public static final String COLUMN_DIALECT_NAME_INSERT = "dialect_name text";

    //GENDERS
    public static final String TABLE_GENDER = "gender";
    public static final String COLUMN_GENDER_NAME = "gender_name";
    public static final String COLUMN_GENDER_NAME_INSERT = "gender_name text";

    //CATEGORIES
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_NAME = "category_name";
    public static final String COLUMN_CATEGORY_NAME_INSERT = "category_name text";

    private static final String CREATE_HABIBI_PHRASE =
            CREATE_TABLE + TABLE_HABIBI_PHRASE
            + "(" + COLUMN_ID_INSERT
            +       COLUMN_CATEGORY_INSERT
            + " ); ";

    private static final String CREATE_PHRASE =
            CREATE_TABLE + TABLE_PHRASE
            + "(" + COLUMN_ID_INSERT
            +       COLUMN_HABIBI_PHRASE_INSERT
            +       COLUMN_LANGUAGE_INSERT
            +       COLUMN_DIALECT_INSERT
            +       COLUMN_FROM_GENDER_INSERT
            +       COLUMN_TO_GENDER_INSERT
            +       COLUMN_NATIVE_PHRASE_STRING_INSERT
            +       COLUMN_PHONETIC_SPELLING_INSERT
            +       COLUMN_PROPER_PHONTETIC_SPELLING_INSERT
            + ");";

    private static final String CREATE_LANGUAGE =
            CREATE_TABLE + TABLE_LANGUAGE
            + "("
            +       COLUMN_ID_INSERT
            +       COLUMN_LANGUAGE_NAME_INSERT
            + ");";

    private static final String CREATE_DIALECT =
            CREATE_TABLE + TABLE_DIALECT
            + "("
            +       COLUMN_ID_INSERT
            +       COLUMN_DIALECT_NAME_INSERT
            + ");";

    private static final String CREATE_GENDER =
            CREATE_TABLE + TABLE_GENDER
            + "("
            +       COLUMN_ID_INSERT
            +       COLUMN_GENDER_NAME_INSERT
            + ");";

    private static final String CREATE_CATEGORY =
            CREATE_TABLE + TABLE_CATEGORY
            + "("
            +       COLUMN_ID_INSERT
            +       COLUMN_CATEGORY_NAME_INSERT
            + ");";

    public static final int CATEGORY = 0;
    public static final int ENGLISH = 1;
    public static final int ARABIC_M = 2;
    public static final int BIZI_M = 3;
    public static final int BIZIPROPER_M = 4;
    public static final int ARABIC_F = 5;
    public static final int BIZI_F = 6;
    public static final int BIZIPROPER_F = 7;

    private SQLiteDatabase db;
    private Context context;

    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        dropTables();
        onCreate(db);
    }

    public void setupDatabase() {
        try {
//            if (!tableExists(TABLE_HABIBI_PHRASE)) {
                this.db.execSQL(CREATE_HABIBI_PHRASE);
//            }
//            if (!tableExists(TABLE_PHRASE)) {
                this.db.execSQL(CREATE_PHRASE);
//            }
//            if (!tableExists(TABLE_LANGUAGE)) {
                this.db.execSQL(CREATE_LANGUAGE);
//            }
//            if (!tableExists(TABLE_DIALECT)) {
                this.db.execSQL(CREATE_DIALECT);
//            }
//            if (!tableExists(TABLE_GENDER)) {
                this.db.execSQL(CREATE_GENDER);
//            }
//            if (!tableExists(TABLE_CATEGORY)) {
                this.db.execSQL(CREATE_CATEGORY);
//            }
        } catch  (Exception ex) {
            Log.e("SQL", "Couldn't write initial DB: " + ex.toString());
        }
    }

    public void loadDatabase() {
        HabibiPhraseDataSource habibiPhraseDataSource = null;
        PhraseDataSource phraseDataSource = null;

        //CATEGORIES
        CategoryDataSource categoryDataSource = new CategoryDataSource(context);
        categoryDataSource.open();
        categoryDataSource.createCategory(Category.MOOD.getCategoryName());
        categoryDataSource.createCategory(Category.QUESTION.getCategoryName());
        categoryDataSource.createCategory(Category.ANSWER.getCategoryName());
        categoryDataSource.createCategory(Category.FLIRT.getCategoryName());
        categoryDataSource.close();

        //GENDERS
        GenderDataSource genderDataSource = new GenderDataSource(context);
        genderDataSource.open();
        genderDataSource.createGender(Gender.MALE.getGenderName());
        genderDataSource.createGender(Gender.FEMALE.getGenderName());
        genderDataSource.close();

        //DIALECTS
        DialectDataSource dialectDataSource = new DialectDataSource(context);
        dialectDataSource.open();
        dialectDataSource.createDialect(Dialect.JORDAN.getDialectName());
        dialectDataSource.close();

        //LANGUAGES
        LanguageDataSource languageDataSource = new LanguageDataSource(context);
        languageDataSource.open();
        languageDataSource.createLanguage(Language.ARABIC.getLanguageName());
        languageDataSource.createLanguage(Language.ENGLISH.getLanguageName());
        languageDataSource.close();

        //PHRASES
        try {
            habibiPhraseDataSource = new HabibiPhraseDataSource(context);
            phraseDataSource = new PhraseDataSource(context);
            habibiPhraseDataSource.open();
            phraseDataSource.open();
            InputStream inputStream = context.getResources().openRawResource(R.raw.dictionary_new);
            InputStreamReader inputreader = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            do {
                line = buffreader.readLine();
                addPhrase(habibiPhraseDataSource, phraseDataSource, line);
            } while (line != null);
            inputreader.close();
            buffreader.close();
            habibiPhraseDataSource.close();
            phraseDataSource.close();
        } catch (Throwable throwable) {
            Log.e("Loading Database", "Couldn't add words: " + throwable.toString());
        } finally {
            habibiPhraseDataSource.close();
            phraseDataSource.close();
        }
    }

    private void addPhrase(HabibiPhraseDataSource habibiPhraseDataSource, PhraseDataSource phraseDataSource, String csvLine){
        String[] line = csvLine.split(", ");

        if (line.length != 8) {
            Log.e("add phrase",  "we've got a problem: " + line.length + " with: " + csvLine);
            for (int i = 0; i < line.length; i++) {
                Log.e("add phrase", line[i]);
            }
            return;
        }

        try {
            //Create Habibi Phrase
            Category category = Category.getCategoryFromName(line[CATEGORY].trim());
            long habibiId = habibiPhraseDataSource.createHabibiPhrase(category);

            //Create English Phrase
            phraseDataSource.createPhrase(habibiId, Language.ENGLISH.getId(),
                    -1, -1, -1, line[ENGLISH], null, null);

            if (Category.MOOD.equals(category)) {
                //Create Arabic Phrase M->M
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.MALE.getId(), Gender.MALE.getId(),
                        line[ARABIC_M], line[BIZI_M], line[BIZIPROPER_M]);

                //Create Arabic Phrase M->F
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.MALE.getId(), Gender.FEMALE.getId(),
                        line[ARABIC_M], line[BIZI_M], line[BIZIPROPER_M]);

                //Create Arabic Phrase F->F
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.FEMALE.getId(), Gender.FEMALE.getId(),
                        line[ARABIC_F], line[BIZI_F], line[BIZIPROPER_F]);

                //Create Arabic Phrase F->M
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.FEMALE.getId(), Gender.MALE.getId(),
                        line[ARABIC_F], line[BIZI_F], line[BIZIPROPER_F]);
            } else {
                //Create Arabic Phrase M->M
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.MALE.getId(), Gender.MALE.getId(),
                        line[ARABIC_M], line[BIZI_M], line[BIZIPROPER_M]);

                //Create Arabic Phrase F->M
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.FEMALE.getId(), Gender.MALE.getId(),
                        line[ARABIC_M], line[BIZI_M], line[BIZIPROPER_M]);

                //Create Arabic Phrase F->F
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.FEMALE.getId(), Gender.FEMALE.getId(),
                        line[ARABIC_F], line[BIZI_F], line[BIZIPROPER_F]);

                //Create Arabic Phrase M->F
                phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(),
                        Dialect.JORDAN.getId(), Gender.MALE.getId(), Gender.FEMALE.getId(),
                        line[ARABIC_F], line[BIZI_F], line[BIZIPROPER_F]);
            }


        } catch (Exception e) {
            Log.e("Add phrase", "Phrase add fail!" + e.toString());
        }

    }

    public void dropTables() {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABIBI_PHRASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHRASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIALECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
    }

    private boolean tableExists(String tableName) {
        Cursor cursor = db.rawQuery("select 1 from " + tableName + " LIMIT 1", null);
        return true;
    }

} 