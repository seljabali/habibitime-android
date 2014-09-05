package com.codingcamels.habibitime.installation.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.OnSwipeTouchListener;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Gender;

/**
 * Created by habibi on 8/3/14.
 */
public class GenderSelectFragment extends Fragment {
    private boolean selectedSelfGender;
    private boolean selectedHabibiGender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender_select, null);
        if (view == null) {
            return null;
        }
        final ImageView selfGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        final ImageView selfGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        final ImageView habibiGenderMale = (ImageView) view.findViewById(R.id.gender_select_habibi_m_image_view);
        final ImageView habibiGenderFemale = (ImageView) view.findViewById(R.id.gender_select_habibi_f_image_view);

        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextPage();
                    }
        });

        final SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        selfGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.FROM_GENDER, Gender.MALE.getIdAsString()).commit();
                selectedSelfGender = true;
                setGenderSelect(selfGenderMale, selfGenderFemale);
            }
        });
        selfGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.FROM_GENDER, Gender.FEMALE.getIdAsString()).commit();
                selectedSelfGender = true;
                setGenderSelect(selfGenderFemale, selfGenderMale);
            }
        });
        habibiGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.TO_GENDER, Gender.MALE.getIdAsString()).commit();
                selectedHabibiGender = true;
                setGenderSelect(habibiGenderMale, habibiGenderFemale);
            }
        });
        habibiGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSettings.edit().putString(MainActivity.TO_GENDER, Gender.FEMALE.getIdAsString()).commit();
                selectedHabibiGender = true;
                setGenderSelect(habibiGenderFemale, habibiGenderMale);
            }
        });

        return view;
    }

    private void setGenderSelect(View selectedGender, View unselectedGender) {
        selectedGender.setBackground(getActivity().getResources().getDrawable(R.drawable.border));
        unselectedGender.setBackgroundColor(android.R.color.transparent);
    }

    private void previousPage() {
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

    private void nextPage() {
        //TODO MAKE THIS WORK
        if (selectedSelfGender && selectedHabibiGender) {
            //TODO: Have defined default values
            SharedPreferences sharedSettings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
            sharedSettings.edit().putBoolean(MainActivity.FIRST_TIME, false).commit();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
