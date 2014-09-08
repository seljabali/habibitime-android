package com.codingcamels.habibitime.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Gender;

/**
 * Created by samsoom on 9/4/14.
 */
public class SettingsFragment extends Fragment {

    private SharedPreferences appSettings;
    private ImageView selfGenderMale;
    private ImageView selfGenderFemale;
    private Drawable selectedBorder;
    private int transparent;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        selfGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        selfGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        appSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        selectedBorder = getActivity().getResources().getDrawable(R.drawable.border);
        transparent = android.R.color.transparent;

        setFromGender(MainActivity.getFromGenderSettings(getActivity()));
        selfGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFromGender(Gender.MALE);
            }
        });
        selfGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFromGender(Gender.FEMALE);
            }
        });

        final CheckBox enableMiniView = (CheckBox) view.findViewById(R.id.enable_mini_view);
        enableMiniView.setChecked(MainActivity.isBibiEnabled(getActivity()));
        enableMiniView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appSettings.edit().putBoolean(MainActivity.MINI_BIBI, enableMiniView.isChecked()).commit();
                MainActivity.setUpBubble(getActivity());
            }
        });
        return view;
    }

    private void setFromGender(Gender gender) {
        appSettings.edit().putString(MainActivity.FROM_GENDER, gender.getIdAsString()).commit();
        if (Gender.MALE.equals(gender)) {
            selfGenderMale.setBackground(selectedBorder);
            selfGenderFemale.setBackgroundColor(transparent);
        } else {
            selfGenderFemale.setBackground(selectedBorder);
            selfGenderMale.setBackgroundColor(transparent);
        }
    }
}
