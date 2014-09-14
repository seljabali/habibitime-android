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
    private boolean selfGenderSelected;
    private boolean habibiGenderSelected;
    private ImageView selfGenderMale;
    private ImageView selfGenderFemale;
    private ImageView toGenderMale;
    private ImageView toGenderFemale;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_install_gender_select, null);
        if (view == null) {
            return null;
        }
        selfGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        selfGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        toGenderMale = (ImageView) view.findViewById(R.id.gender_select_habibi_m_image_view);
        toGenderFemale = (ImageView) view.findViewById(R.id.gender_select_habibi_f_image_view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //From Gender
        selfGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfGenderSelected = true;
                MainActivity.setFromGender(getActivity(), Gender.MALE);
                setGenderSelect(selfGenderMale, selfGenderFemale);
                nextPage();
            }
        });
        selfGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfGenderSelected = true;
                MainActivity.setFromGender(getActivity(), Gender.FEMALE);
                setGenderSelect(selfGenderFemale, selfGenderMale);
                nextPage();
            }
        });

        //To Gender
        toGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habibiGenderSelected = true;
                MainActivity.setToGender(getActivity(), Gender.MALE);
                setGenderSelect(toGenderMale, toGenderFemale);
                nextPage();
            }
        });
        toGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habibiGenderSelected = true;
                MainActivity.setToGender(getActivity(), Gender.FEMALE);
                setGenderSelect(toGenderFemale, toGenderMale);
                nextPage();
            }
        });
    }

    private void setGenderSelect(View selectedGender, View unselectedGender) {
        selectedGender.setBackground(getResources().getDrawable(R.drawable.border));
        unselectedGender.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void nextPage() {
        if (selfGenderSelected && habibiGenderSelected) {
            MainActivity.setFirstTimeUser(getActivity(), false);
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
}
