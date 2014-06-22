//package com.habibiapp.habibi.fragments;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.habibiapp.habibi.MainActivity;
//import com.habibiapp.habibi.R;
//import com.habibiapp.habibi.models.HabibiPhrase;
//
//import java.util.List;
//
///**
//* Created by habibi on 5/8/14.
//*/
//public class ViewPhrasesFragment extends Fragment {
//    public static String TAG = ViewPhrasesFragment.class.getSimpleName();
//    private static final String CATEGORY_FIRST_KEY = "category_first";
//    private static final String CATEGORY_SECOND_KEY = "category_second";
//    private MainActivity mainActivity;
//
//    public static ViewPhrasesFragment newInstance(HabibiPhrase.CategoryFirst categoryFirst, HabibiPhrase.CategorySecond categorySecond) {
//        ViewPhrasesFragment fragment = new ViewPhrasesFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(CATEGORY_FIRST_KEY, categoryFirst);
//        args.putSerializable(CATEGORY_SECOND_KEY, categorySecond);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Bundle itemBundle = getArguments();
//        HabibiPhrase.CategoryFirst categoryFirst = (HabibiPhrase.CategoryFirst) itemBundle.getSerializable(CATEGORY_FIRST_KEY);
//        HabibiPhrase.CategorySecond categorySecond = (HabibiPhrase.CategorySecond) itemBundle.getSerializable(CATEGORY_SECOND_KEY);
//
//        View view = inflater.inflate(R.layout.fragment_view_list_of_phrases, container, false);
//        mainActivity = (MainActivity) getActivity();
//        mainActivity.setHeaderTitle(categoryFirst, categorySecond, null);
//
//        PhraseManager phraseManager = new PhraseManager(mainActivity);
//        List<HabibiPhrase> habibiPhrases = phraseManager.getPhrases(categoryFirst, categorySecond);
//        PhrasesAdapter phrasesAdapter = new PhrasesAdapter(mainActivity, habibiPhrases);
//
//        ListView listOfPhrasesView = (ListView) view.findViewById(R.id.list_phrases_listview);
//        listOfPhrasesView.setAdapter(phrasesAdapter);
//
//        return view;
//    }
//
//}
