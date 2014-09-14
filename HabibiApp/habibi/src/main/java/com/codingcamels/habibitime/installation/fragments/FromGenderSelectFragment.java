package com.codingcamels.habibitime.installation.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
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
public class FromGenderSelectFragment extends Fragment {
    public static final String TAG = FromGenderSelectFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_install_gender_select, null);
        if (view == null) {
            return null;
        }
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

        return view;
    }

    private void setGenderSelect(View selectedGender, View unselectedGender) {
        selectedGender.setBackground(getActivity().getResources().getDrawable(R.drawable.border));
        unselectedGender.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
    }

    private void nextPage() {
        ToGenderSelectFragment fragment = new ToGenderSelectFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit,
                            R.anim.fragment_slide_right_enter,
                            R.anim.fragment_slide_right_exit)
                    .replace(R.id.fragmentLayoutContainer_installation, fragment)
                    .addToBackStack(ToGenderSelectFragment.TAG)
                    .commit();
        }
    }
}
