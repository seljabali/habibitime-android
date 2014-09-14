package com.codingcamels.habibitime.installation.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Gender;

/**
 * Created by habibi on 8/3/14.
 */
public class GenderSelectFragment extends Fragment {
    public static final String TAG = GenderSelectFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_install_gender_select, null);
        if (view == null) {
            return null;
        }
        //From Gender
        final ImageView selfGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        final ImageView selfGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        selfGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setFromGender(getActivity(), Gender.MALE);
                setGenderSelect(selfGenderMale, selfGenderFemale);
                nextPage();
            }
        });
        selfGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setFromGender(getActivity(), Gender.FEMALE);
                setGenderSelect(selfGenderFemale, selfGenderMale);
                nextPage();
            }
        });

        //To Gender
        final TextView titleTextView = (TextView) view.findViewById(R.id.title);
        final TextView subTitleTextView = (TextView) view.findViewById(R.id.subtitle);
        final ImageView toGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        final ImageView toGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        titleTextView.setText(getActivity().getResources().getString(R.string.select_to_gender_title));
        subTitleTextView.setText(getActivity().getResources().getString(R.string.select_to_gender_subtitle));
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
        BibiEnableFragment fragment = new BibiEnableFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit,
                            R.anim.fragment_slide_right_enter,
                            R.anim.fragment_slide_right_exit)
                    .replace(R.id.fragmentLayoutContainer_installation, fragment)
                    .addToBackStack(BibiEnableFragment.TAG)
                    .commit();
        }
    }
}
