package com.habibiapp.habibi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.habibiapp.habibi.datasources.CategoryDataSource;
import com.habibiapp.habibi.fragments.ViewCategoriesFragment;
import com.habibiapp.habibi.models.Category;
import java.util.List;

public class MainActivity extends Activity {
    private MySQLHelper mySQLHelper;
    private final String PREFS_NAME = "MyPrefsFile";
    private final String FIRST_TIME = "my_first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean(FIRST_TIME, true)) {
            mySQLHelper = new MySQLHelper(this);
            mySQLHelper.dropTables();
            mySQLHelper.setupDatabase();
            mySQLHelper.loadDatabase();
            settings.edit().putBoolean(FIRST_TIME, false).commit();
        }

        CategoryDataSource categoryDataSource = new CategoryDataSource(this);
        categoryDataSource.open();
        List<Category> categories = categoryDataSource.getCategories();
        categoryDataSource.close();

        ViewCategoriesFragment fragment = ViewCategoriesFragment.newInstance(categories);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer, fragment, ViewCategoriesFragment.TAG)
                .addToBackStack(ViewCategoriesFragment.TAG)
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
}