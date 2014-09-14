package com.codingcamels.habibitime.installation.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Gender;

/**
 * Created by habibi on 8/3/14.
 */
public class ToGenderSelectFragment extends Fragment {
    public static final String TAG = FromGenderSelectFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_install_gender_select, null);
        if (view == null) {
            return null;
        }
        final ImageView toGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        final ImageView toGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);

        toGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setToGender(getActivity(), Gender.MALE);
                setGenderSelect(toGenderMale, toGenderFemale);
                nextPage();
            }
        });
        toGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setToGender(getActivity(), Gender.FEMALE);
                setGenderSelect(toGenderFemale, toGenderMale);
                nextPage();
            }
        });

        return view;
    }

    private void setGenderSelect(View selectedGender, View unselectedGender) {
        selectedGender.setBackground(getActivity().getResources().getDrawable(R.drawable.border));
        unselectedGender.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
    }

    private void nextPage() {
        Activity activity = getActivity();
        SharedPreferences sharedSettings = activity.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        sharedSettings.edit().putBoolean(MainActivity.FIRST_TIME, false).commit();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        activity.finish();
    }
}
