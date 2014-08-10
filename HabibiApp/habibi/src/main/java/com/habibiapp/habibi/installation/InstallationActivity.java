package com.habibiapp.habibi.installation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.habibiapp.habibi.R;
import com.habibiapp.habibi.installation.fragments.WelcomePageFragment;

public class InstallationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
