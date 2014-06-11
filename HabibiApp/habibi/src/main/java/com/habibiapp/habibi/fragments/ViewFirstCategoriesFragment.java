package com.habibiapp.habibi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.models.HabibiPhrase;

/**
 * Created by habibi on 5/7/14.
 */
public class ViewFirstCategoriesFragment extends Fragment {
    public static String TAG = ViewFirstCategoriesFragment.class.getSimpleName();
    private MainActivity mainActivity;

    public static ViewFirstCategoriesFragment newInstance() {
        ViewFirstCategoriesFragment fragment = new ViewFirstCategoriesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_view_first_categories, container, false);

        mainActivity = (MainActivity)getActivity();

        final Button btnFlirt = (Button) view.findViewById(R.id.Flirt);
        btnFlirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.launchSecondaryCategoryFragment(HabibiPhrase.CategoryFirst.valueOf(btnFlirt.getText().toString()));
            }
        });

        final Button btnMeetup = (Button) view.findViewById(R.id.Meetup);
        btnMeetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.launchSecondaryCategoryFragment(HabibiPhrase.CategoryFirst.valueOf(btnMeetup.getText().toString()));
            }
        });

        final Button btnMood = (Button) view.findViewById(R.id.Mood);
        btnMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.launchSecondaryCategoryFragment(HabibiPhrase.CategoryFirst.valueOf(btnMood.getText().toString()));
            }
        });

        final Button btnAnswer = (Button) view.findViewById(R.id.Answer);
        btnMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.launchSecondaryCategoryFragment(HabibiPhrase.CategoryFirst.valueOf(btnAnswer.getText().toString()));
            }
        });

        return view;
    }

}
