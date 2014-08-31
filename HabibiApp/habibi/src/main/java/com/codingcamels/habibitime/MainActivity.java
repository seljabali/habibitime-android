package com.codingcamels.habibitime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codingcamels.habibitime.fragments.ViewCategoriesFragment;
import com.codingcamels.habibitime.installation.InstallationActivity;
import com.codingcamels.habibitime.models.Gender;

public class MainActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String FIRST_TIME = "my_first_time";
    public static final String FROM_GENDER = "from_gender";
    public static final String TO_GENDER = "to_gender";
    public static final String MINI_BIBI = "mini_habibi_time";

    public static final String COPY_TYPE = "copy_type";
    private SharedPreferences sharedSettings;
    private SharedPreferences appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appSettings = PreferenceManager.getDefaultSharedPreferences(this);
        sharedSettings = getSharedPreferences(PREFS_NAME, 0);
        if (sharedSettings.getBoolean(FIRST_TIME, true)) {
            Intent intent = new Intent(this, InstallationActivity.class);
            startActivity(intent);
            finish();
        } else {
            setUpBubble();
            setContentView(R.layout.activity_main);
            ViewCategoriesFragment fragment = ViewCategoriesFragment.newInstance(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayoutContainer, fragment, ViewCategoriesFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        setUpBubble();
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

    public static Gender getFromGenderSettings(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String fromGender = appSettings.getString(FROM_GENDER, "1");
        return Gender.getGenderFromID(fromGender);
    }

    public static Gender getToGenderSettings(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String toGender = appSettings.getString(TO_GENDER, "2");
        return Gender.getGenderFromID(toGender);
    }

    public static int getCopyTypeSettings(Context context) {
        return 1;
//        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
//        String toGender = appSettings.getString(TO_GENDER, "2");
//        return Gender.getGenderFromID(toGender);
    }

    private void setUpBubble() {
        if (appSettings.getBoolean(MINI_BIBI, false)) {
            startService(new Intent(this, BubbleService.class));
        } else {
            stopService(new Intent(this, BubbleService.class));
        }
    }

}