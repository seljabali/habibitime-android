package com.habibiapp.habibi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.habibiapp.habibi.fragments.ViewCategoriesFragment;
import com.habibiapp.habibi.installation.InstallationActivity;
import com.habibiapp.habibi.installation.fragments.WelcomePageFragment;
import com.habibiapp.habibi.models.Gender;

public class MainActivity extends Activity {
    private MySQLHelper mySQLHelper;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String FIRST_TIME = "my_first_time";
    public static final String FROM_GENDER = "from_gender";
    public static final String TO_GENDER = "to_gender";
    private SharedPreferences sharedSettings;
    private SharedPreferences appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, BubbleService.class));

        appSettings = PreferenceManager.getDefaultSharedPreferences(this);
        sharedSettings = getSharedPreferences(PREFS_NAME, 0);
        if (sharedSettings.getBoolean(FIRST_TIME, true)) {
            mySQLHelper = new MySQLHelper(this);
            mySQLHelper.dropTables();
            mySQLHelper.setupDatabase();
            mySQLHelper.loadDatabase();
            Intent intent = new Intent(this, InstallationActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            ViewCategoriesFragment fragment = ViewCategoriesFragment.newInstance(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayoutContainer, fragment, ViewCategoriesFragment.TAG)
                    .commit();
        }
//            mySQLHelper = new MySQLHelper(this);
//            mySQLHelper.dropTables();
//            mySQLHelper.setupDatabase();
//            mySQLHelper.loadDatabase();
//            sharedSettings.edit().putBoolean(FIRST_TIME, false).commit();
//        }
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
        String fromGender = appSettings.getString(FROM_GENDER, "1");
        return Gender.getGenderFromID(fromGender);
    }

    public Gender getToGenderSettings() {
        String toGender = appSettings.getString(TO_GENDER, "2");
        return Gender.getGenderFromID(toGender);
    }
}