package com.habibiapp.habibi.installation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.habibiapp.habibi.OnSwipeTouchListener;
import com.habibiapp.habibi.R;

/**
 * Created by habibi on 8/3/14.
 */
public class AboutPageFragment extends Fragment {
    public static final String TAG = AboutPageFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_page, null);
        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                GenderSelectFragment fragment = new GenderSelectFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_right_exit)
                        .replace(R.id.fragmentLayoutContainer_installation, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onSwipeRight() {
                WelcomePageFragment fragment = new WelcomePageFragment();
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
