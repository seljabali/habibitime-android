package com.codingcamels.habibitime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.codingcamels.habibitime.fragments.ViewCategoriesFragment;
import com.codingcamels.habibitime.installation.InstallationActivity;
import com.codingcamels.habibitime.models.Gender;

public class MainActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String FIRST_TIME = "my_first_time";
    public static final String FROM_GENDER = "from_gender";
    public static final String TO_GENDER = "to_gender";
    public static final String BIBI = "mini_habibi_time";
    public static final String PASTE_TYPE = "paste_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFirstTimeUser(this)) {
            Intent intent = new Intent(this, InstallationActivity.class);
            startActivity(intent);
            finish();
        } else {
            setUpBibi(this, isBibiEnabled(this));
            setContentView(R.layout.activity_main);
            ViewCategoriesFragment fragment = ViewCategoriesFragment.newInstance(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayoutContainer, fragment, ViewCategoriesFragment.TAG)
                    .commit();
        }
    }

    //first time user
    public static boolean isFirstTimeUser(Context context) {
        SharedPreferences sharedSettings = context.getSharedPreferences(PREFS_NAME, 0);
        return sharedSettings.getBoolean(FIRST_TIME, true);
    }

    public static void setFirstTimeUser(Context context, boolean firstTime) {
        SharedPreferences sharedSettings = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        sharedSettings.edit().putBoolean(MainActivity.FIRST_TIME, firstTime).commit();
    }

    //from gender
    public static Gender getFromGenderSettings(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String fromGender = appSettings.getString(FROM_GENDER, "1");
        return Gender.getGenderFromID(fromGender);
    }
    public static void setFromGender(Context context, Gender fromGender) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putString(MainActivity.FROM_GENDER, fromGender.getIdAsString()).commit();
    }

    //to gender
    public static Gender getToGenderSettings(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String toGender = appSettings.getString(TO_GENDER, "2");
        return Gender.getGenderFromID(toGender);
    }
    public static void setToGender(Context context, Gender toGender) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putString(MainActivity.TO_GENDER, toGender.getIdAsString()).commit();
    }

    //paste settings
    public static void setPasteTypeSetting(Context context, String type) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putString(MainActivity.PASTE_TYPE, type).commit();
    }

    public static String getPasteTypeSetting(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        return appSettings.getString(MainActivity.PASTE_TYPE, "Arabizi");
    }


    //bibi settings
    public static void setUpBibi(Activity activity, boolean enabled) {
        if (enabled) {
            activity.startService(new Intent(activity, BibiService.class));
        } else {
            activity.stopService(new Intent(activity, BibiService.class));
        }
    }

    public static void setBibiEnabled(Context context, boolean enabled) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putBoolean(MainActivity.BIBI, enabled).commit();
    }

    public static boolean isBibiEnabled(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        return appSettings.getBoolean(BIBI, false);
    }

}