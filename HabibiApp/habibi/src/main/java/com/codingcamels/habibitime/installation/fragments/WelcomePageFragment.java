package com.codingcamels.habibitime.installation.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingcamels.habibitime.MySQLHelper;
import com.codingcamels.habibitime.R;

/**
 * Created by habibi on 8/2/14.
 */
public class WelcomePageFragment extends Fragment {
    public static final String TAG = WelcomePageFragment.class.getSimpleName();
    private boolean dbSetup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_install_welcome_page, null);
        if (view == null) {
            return null;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbSetup) {
                    nextFragment();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!dbSetup) {
            setupDatabase();
            dbSetup = true;
        }
    }

    private void nextFragment() {
        FromGenderSelectFragment fragment = new FromGenderSelectFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit)
                .replace(R.id.fragmentLayoutContainer_installation, fragment)
                .addToBackStack(FromGenderSelectFragment.TAG)
                .commit();
        }
    }

    private void setupDatabase() {
        MySQLHelper mySQLHelper = new MySQLHelper(getActivity());
        mySQLHelper.dropTables();
        mySQLHelper.setupDatabase();
        mySQLHelper.loadDatabase();
    }
}
