package com.habibiapp.habibi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.habibiapp.habibi.datasources.CategoryDataSource;
import com.habibiapp.habibi.datasources.DialectDataSource;
import com.habibiapp.habibi.datasources.GenderDataSource;
import com.habibiapp.habibi.datasources.HabibiPhraseDataSource;
import com.habibiapp.habibi.datasources.LanguageDataSource;
import com.habibiapp.habibi.datasources.PhraseDataSource;
import com.habibiapp.habibi.models.Category;
import com.habibiapp.habibi.models.Dialect;
import com.habibiapp.habibi.models.Gender;
import com.habibiapp.habibi.models.Language;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends Activity {
    private MySQLHelper mySQLHelper;
    private final String PREFS_NAME = "MyPrefsFile";
    private final String FIRST_TIME = "my_first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        if (settings.getBoolean(FIRST_TIME, true)) {
        mySQLHelper = new MySQLHelper(this);
        mySQLHelper.dropTables();
        mySQLHelper.setupDatabase();
        mySQLHelper.loadDatabase();
//            settings.edit().putBoolean(FIRST_TIME, false).commit();
//        }

        CategoryDataSource categoryDataSource = new CategoryDataSource(this);
        categoryDataSource.open();
        List<Category> categories = categoryDataSource.getCategories();
        categoryDataSource.close();
        for (int i = 0; i < categories.size(); i++) {
            Log.d("Category", categories.get(i).getCategoryName());
        }
//        ViewSecondCategoriesFragment fragment = ViewSecondCategoriesFragment.newInstance(categoryFirst);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentLayoutContainer, fragment, ViewFirstCategoriesFragment.TAG)
//                .addToBackStack(ViewFirstCategoriesFragment.TAG)
//                .commit();
    }

}