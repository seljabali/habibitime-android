package com.habibiapp.habibi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.habibiapp.habibi.datasources.CategoryDataSource;
import com.habibiapp.habibi.fragments.ViewCategoriesFragment;
import com.habibiapp.habibi.models.Category;
import com.habibiapp.habibi.models.Gender;

import java.util.List;

public class MainActivity extends Activity {
    private MySQLHelper mySQLHelper;
    private final String PREFS_NAME = "MyPrefsFile";
    private final String FIRST_TIME = "my_first_time";
    private SharedPreferences sharedSettings;
    private SharedPreferences appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSettings = PreferenceManager.getDefaultSharedPreferences(this);
        sharedSettings = getSharedPreferences(PREFS_NAME, 0);
//        if (settings.getBoolean(FIRST_TIME, true)) {
//            mySQLHelper = new MySQLHelper(this);
//            mySQLHelper.dropTables();
//            mySQLHelper.setupDatabase();
//            mySQLHelper.loadDatabase();
//            settings.edit().putBoolean(FIRST_TIME, false).commit();
//        }

        CategoryDataSource categoryDataSource = new CategoryDataSource(this);
        categoryDataSource.open();
        List<Category> categories = categoryDataSource.getCategories();
        categoryDataSource.close();

        ViewCategoriesFragment fragment = ViewCategoriesFragment.newInstance(categories);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer, fragment, ViewCategoriesFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, R.string.settings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    public Gender getFromGenderSettings() {
        String fromGender = appSettings.getString("from_gender", "1");
        return Gender.getGenderFromID(fromGender);
    }

    public Gender getToGenderSettings() {
        String toGender = appSettings.getString("to_gender", "1");
        return Gender.getGenderFromID(toGender);
    }
}