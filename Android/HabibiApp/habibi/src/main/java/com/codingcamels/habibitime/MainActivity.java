package com.codingcamels.habibitime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codingcamels.habibitime.bibi.BibiService;
import com.codingcamels.habibitime.fragments.AddPhraseFragment;
import com.codingcamels.habibitime.fragments.ViewCategoriesFragment;
import com.codingcamels.habibitime.installation.InstallationActivity;
import com.codingcamels.habibitime.models.*;
import com.codingcamels.habibitime.utilities.SoundUtils;
import com.crashlytics.android.Crashlytics;

import java.io.File;

public class MainActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String FIRST_TIME = "my_first_time";
    public static final String FROM_GENDER = "from_gender";
    public static final String TO_GENDER = "to_gender";
    public static final String BIBI = "mini_habibi_time";
    public static final String PASTE_TYPE = "paste_type";

    public static final boolean BIBI_ENABLED = false;

    public static final boolean USE_SD = false;
    public static final String AUDIO_RECORDER_FOLDER = "HabibiTimeAudio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        if (isFirstTimeUser(this)) {
            Intent intent = new Intent(this, InstallationActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        } else {
            setContentView(R.layout.activity_main);
            ViewCategoriesFragment fragment = ViewCategoriesFragment.newInstance(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayoutContainer, fragment, ViewCategoriesFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);

        final MenuItem addItem = menu.findItem(R.id.add);
        addItem.setIcon(R.drawable.plus);

        final MenuItem minimizeItem = menu.findItem(R.id.minimize);
        minimizeItem.setIcon(R.drawable.minimize);
        minimizeItem.setVisible(BIBI_ENABLED);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case R.id.minimize:
                if (BIBI_ENABLED) {
                    MainActivity.setUpBibi(this, true);
                    MainActivity.this.finish();
                    getFragmentManager().executePendingTransactions();
                }
                break;
            case R.id.add:
                showAddPhraseFragment();
        }
        return true;
    }

    public static void setUpBibi(Activity activity, boolean enabled) {
        if (enabled) {
            activity.stopService(new Intent(activity, BibiService.class));
            activity.getFragmentManager().executePendingTransactions();
            activity.startService(new Intent(activity, BibiService.class));
            activity.getFragmentManager().executePendingTransactions();
        } else {
            activity.stopService(new Intent(activity, BibiService.class));
            activity.getFragmentManager().executePendingTransactions();
        }
    }

    private void showAddPhraseFragment() {
        AddPhraseFragment fragment = AddPhraseFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer, fragment, AddPhraseFragment.TAG)
                .addToBackStack(AddPhraseFragment.TAG)
                .commit();
    }

    public static void playSound(Context context, String fileName) {
        String filePath = getSoundDirectory() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            SoundUtils.playSound(filePath);
        } else {
            SoundUtils.playSoundFromResources(context, fileName);
        }
    }

    public static String getSoundDirectory() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        if (!file.exists()) {
            return "";
        }
        return file.getAbsolutePath() + "/";
    }

    public static void createExternalSoundDirectory() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    //TODO: Move to separate class
    /**** USER SETTINGS ****/
    public static boolean isFirstTimeUser(Context context) {
        SharedPreferences sharedSettings = context.getSharedPreferences(PREFS_NAME, 0);
        return sharedSettings.getBoolean(FIRST_TIME, true);
    }

    public static void setFirstTimeUser(Context context, boolean firstTime) {
        SharedPreferences sharedSettings = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        sharedSettings.edit().putBoolean(MainActivity.FIRST_TIME, firstTime).commit();
    }

    public static Gender getFromGenderSettings(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String fromGender = appSettings.getString(FROM_GENDER, "1");
        return Gender.getGenderFromID(fromGender);
    }
    public static void setFromGender(Context context, Gender fromGender) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putString(MainActivity.FROM_GENDER, fromGender.getIdAsString()).commit();
    }

    public static Gender getToGenderSettings(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String toGender = appSettings.getString(TO_GENDER, "2");
        return Gender.getGenderFromID(toGender);
    }
    public static void setToGender(Context context, Gender toGender) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putString(MainActivity.TO_GENDER, toGender.getIdAsString()).commit();
    }

    public static void setPasteTypeSetting(Context context, String type) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        appSettings.edit().putString(MainActivity.PASTE_TYPE, type).commit();
    }

    public static String getPasteTypeSetting(Context context) {
        SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(context);
        return appSettings.getString(MainActivity.PASTE_TYPE, "Arabizi");
    }

}