package com.codingcamels.habibitime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.codingcamels.habibitime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsoom on 1/16/15.
 */
public class AddPhraseFragment extends Fragment {
    @InjectView(R.id.fromGenderToGenderPhraseViewGroup)
    ViewGroup fromGenderToGenderPhraseViewGroup;
    @InjectView(R.id.copyFemaleToFemale)
    CheckBox copyFemaleToFemale;
    @InjectView(R.id.copyMaleToMale)
    CheckBox copyMaleToMale;
    @InjectView(R.id.englishEditText)
    EditText editEnglish;
//    @InjectView(R.id.phoneticEditText) EditText editPhonetic;
//    @InjectView(R.id.arabiziEditText) EditText editArabizi;
//    @InjectView(R.id.play_back) Button btnPlay;
//    @InjectView(R.id.record) Button btnRecord;

    public static final String TAG = AddPhraseFragment.class.getSimpleName();

    public static AddPhraseFragment newInstance() {
        return new AddPhraseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_phrase, container, false);
        ButterKnife.inject(this, view);
        if (view == null) {
            return null;
        }

        View viewAddArabicPhrase = inflater.inflate(R.layout.view_add_arabic_phrase, container, false);
        fromGenderToGenderPhraseViewGroup.addView(viewAddArabicPhrase);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
    }


}
