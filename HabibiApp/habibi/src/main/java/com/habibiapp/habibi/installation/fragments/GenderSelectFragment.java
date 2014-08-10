package com.habibiapp.habibi.installation.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.OnSwipeTouchListener;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.models.Gender;

/**
 * Created by habibi on 8/3/14.
 */
public class GenderSelectFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender_select, null);
        if (view == null) {
            return null;
        }
        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                ThankYouFragment fragment = new ThankYouFragment();
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
                AboutPageFragment fragment = new AboutPageFragment();
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

        ImageView selfGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        ImageView selfGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        ImageView habibiGenderMale = (ImageView) view.findViewById(R.id.gender_select_habibi_m_image_view);
        ImageView habibiGenderFemale = (ImageView) view.findViewById(R.id.gender_select_habibi_f_image_view);

        final SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        selfGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.FROM_GENDER, Gender.MALE.getIdAsString()).commit();
            }
        });
        selfGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.FROM_GENDER, Gender.FEMALE.getIdAsString()).commit();
            }
        });
        habibiGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.TO_GENDER, Gender.MALE.getIdAsString()).commit();
            }
        });
        habibiGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.TO_GENDER, Gender.FEMALE.getIdAsString()).commit();
            }
        });

        return view;
    }
}
