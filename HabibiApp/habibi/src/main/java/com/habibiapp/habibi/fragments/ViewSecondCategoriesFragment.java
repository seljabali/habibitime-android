package com.habibiapp.habibi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.models.HabibiPhrase;

/**
 * Created by habibi on 5/7/14.
 */
public class ViewSecondCategoriesFragment extends Fragment {

    public static String TAG = ViewFirstCategoriesFragment.class.getSimpleName();
    private static final String CATEGORY_FIRST_KEY = "category_first";
    private MainActivity mainActivity;

    public static ViewSecondCategoriesFragment newInstance(HabibiPhrase.CategoryFirst categoryFirst) {
        ViewSecondCategoriesFragment fragment = new ViewSecondCategoriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORY_FIRST_KEY,categoryFirst);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle itemBundle = getArguments();
        final HabibiPhrase.CategoryFirst categoryFirst = (HabibiPhrase.CategoryFirst)itemBundle.getSerializable(CATEGORY_FIRST_KEY);

        View view = inflater.inflate(R.layout.fragment_view_second_categories, container, false);
        mainActivity = (MainActivity)getActivity();
        mainActivity.setHeaderTitle(categoryFirst, null, null);

        final Button btnI = (Button) view.findViewById(R.id.I);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabibiPhrase.CategorySecond categorySecond = HabibiPhrase.CategorySecond.valueOf(btnI.getText().toString());
                mainActivity.launchListOfPhrasesFragment(categoryFirst, categorySecond);
            }
        });

        final Button btnYou = (Button) view.findViewById(R.id.You);
        btnYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabibiPhrase.CategorySecond categorySecond = HabibiPhrase.CategorySecond.valueOf(btnYou.getText().toString());
                mainActivity.launchListOfPhrasesFragment(categoryFirst, categorySecond);;
            }
        });

        final Button btnQuestion = (Button) view.findViewById(R.id.Question);
        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabibiPhrase.CategorySecond categorySecond = HabibiPhrase.CategorySecond.valueOf(btnQuestion.getText().toString());
                mainActivity.launchListOfPhrasesFragment(categoryFirst, categorySecond);;
            }
        });

        return view;
    }
}
