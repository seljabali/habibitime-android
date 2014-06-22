//package com.habibiapp.habibi.fragments;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.habibiapp.habibi.MainActivity;
//import com.habibiapp.habibi.R;
//import com.habibiapp.habibi.models.HabibiPhrase;
//
///**
//* Created by habibi on 5/8/14.
//*/
//public class ViewPhraseFragment extends Fragment {
//
//        public static String TAG = ViewPhraseFragment.class.getSimpleName();
//        private static final String PHRASE_KEY = "habibiphrase";
//        private MainActivity mainActivity;
//
//        public static ViewPhraseFragment newInstance(HabibiPhrase habibiPhrase) {
//            ViewPhraseFragment fragment = new ViewPhraseFragment();
//            Bundle args = new Bundle();
//            args.putParcelable(PHRASE_KEY, habibiPhrase);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            Bundle itemBundle = getArguments();
//            HabibiPhrase habibiPhrase = itemBundle.getParcelable(PHRASE_KEY);
//            if (habibiPhrase == null) {
//                return null;
//            }
//
//            mainActivity = (MainActivity) getActivity();
//            mainActivity.setHeaderTitle(habibiPhrase.getCategoryFirst(), habibiPhrase.getCategorySecond(), habibiPhrase.getEnglishPhrase().getPhrase());
//
//            View view = inflater.inflate(R.layout.view_habibi_phrase, container, false);
//            TextView arabicTextView = (TextView) view.findViewById(R.id.arabic_phrase);
//            TextView arabiziTextView = (TextView) view.findViewById(R.id.arabizi_phrase);
//            TextView properBiziTextView = (TextView) view.findViewById(R.id.properbizi_phrase);
//
//            arabicTextView.setText(habibiPhrase.getArabicPhrase());
//            arabicTextView.setVisibility(arabicTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
//
//            arabiziTextView.setText(habibiPhrase.getArabicPhraseObject().getPhraseProperBizi());
//            arabiziTextView.setVisibility(arabiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
//
//            properBiziTextView.setText(habibiPhrase.getArabicPhraseObject().getPhraseArabizi());
//            properBiziTextView.setVisibility(properBiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
//
//
//            return view;
//        }
//}
