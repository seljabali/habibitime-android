package com.habibiapp.habibi.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.PhraseAdapter;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.datasources.PhraseDataSource;
import com.habibiapp.habibi.models.Category;
import com.habibiapp.habibi.models.Gender;
import com.habibiapp.habibi.models.Language;
import com.habibiapp.habibi.models.Phrase;

import java.util.List;

/**
* Created by habibi on 5/8/14.
*/
public class ViewPhrasesFragment extends Fragment {
    public static String TAG = ViewPhrasesFragment.class.getSimpleName();
    private MainActivity mainActivity;
    private Category category;

    public static ViewPhrasesFragment newInstance(Category category) {
        ViewPhrasesFragment fragment = new ViewPhrasesFragment();
//        TODO: move to bundle
        fragment.setCategory(category);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_phrases, container, false);
        mainActivity = (MainActivity) getActivity();

        ListView listView = (ListView) view.findViewById(R.id.phrases_listview);

        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        //TODO: GO OFF OF SETTINGS
        List<Phrase> phrases = phraseDataSource.getPhrases(-1, category, null, null, Language.ENGLISH, null);
        phraseDataSource.close();

        PhraseAdapter phraseAdapter = new PhraseAdapter(getActivity(), R.id.category_view_text, phrases, category);
        listView.setAdapter(phraseAdapter);
        return view;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
