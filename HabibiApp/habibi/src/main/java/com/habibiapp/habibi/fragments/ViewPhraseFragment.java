package com.habibiapp.habibi.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.SettingsActivity;
import com.habibiapp.habibi.datasources.PhraseDataSource;
import com.habibiapp.habibi.models.Gender;
import com.habibiapp.habibi.models.Language;
import com.habibiapp.habibi.models.Phrase;

import java.util.List;

/**
* Created by habibi on 5/8/14.
*/
public class ViewPhraseFragment extends Fragment {

        public static String TAG = ViewPhraseFragment.class.getSimpleName();
        private static final String PHRASE_KEY = "phrase";
        private MainActivity mainActivity;
        private Phrase phrase;

        public static ViewPhraseFragment newInstance(Activity activity, Phrase phrase) {
            ViewPhraseFragment fragment = new ViewPhraseFragment();
            PhraseDataSource phraseDataSource = new PhraseDataSource(activity);
            phraseDataSource.open();
            //TODO: GO OFF OF SETTINGS

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
            String toGender = sharedPref.getString("to_gender", "1");
            List<Phrase> translatedPhrases = phraseDataSource.getPhrases(phrase.getHabibiPhraseId(),null, null, Gender.getGenderFromID(toGender), Language.ARABIC, null);
            phraseDataSource.close();
            if (translatedPhrases == null || translatedPhrases.size() != 1) {
                Log.e(TAG, "We got 1 or less translations: " + translatedPhrases.size());
            }
            fragment.setPhrase(translatedPhrases.get(0));
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mainActivity = (MainActivity) getActivity();

            View view = inflater.inflate(R.layout.view_habibi_phrase, container, false);
            TextView arabicTextView = (TextView) view.findViewById(R.id.arabic_phrase);
            TextView arabiziTextView = (TextView) view.findViewById(R.id.arabizi_phrase);
            TextView properBiziTextView = (TextView) view.findViewById(R.id.properbizi_phrase);

            arabicTextView.setText(phrase.getNativePhraseSpelling());
            arabicTextView.setVisibility(arabicTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);

            arabiziTextView.setText(phrase.getPhoneticPhraseSpelling());
            arabiziTextView.setVisibility(arabiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);

            properBiziTextView.setText(phrase.getProperPhoneticPhraseSpelling());
            properBiziTextView.setVisibility(properBiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);


            return view;
        }
        private void setPhrase(Phrase phrase) {
            this.phrase = phrase;
        }
}
