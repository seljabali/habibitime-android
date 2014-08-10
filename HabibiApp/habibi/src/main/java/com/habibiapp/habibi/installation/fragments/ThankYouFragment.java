package com.habibiapp.habibi.installation.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.OnSwipeTouchListener;
import com.habibiapp.habibi.R;

/**
 * Created by habibi on 8/3/14.
 */
public class ThankYouFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thank_you, null);
        if (view == null) {
            return null;
        }
        SharedPreferences sharedSettings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        sharedSettings.edit().putBoolean(MainActivity.FIRST_TIME, false).commit();

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onSwipeRight() {
                GenderSelectFragment fragment = new GenderSelectFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_right_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_left_exit)
                        .replace(R.id.fragmentLayoutContainer_installation, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}
