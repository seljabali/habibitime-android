package com.codingcamels.habibitime.installation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.codingcamels.habibitime.MySQLHelper;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.installation.fragments.WelcomePageFragment;

public class InstallationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupDatabase();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_installation);

        WelcomePageFragment fragment = new WelcomePageFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer_installation, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupDatabase() {
        MySQLHelper mySQLHelper = new MySQLHelper(this);
        mySQLHelper.dropTables();
        mySQLHelper.setupDatabase();
        mySQLHelper.loadDatabase();
    }
}
