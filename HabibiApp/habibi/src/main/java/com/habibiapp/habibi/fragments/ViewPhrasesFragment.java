package com.habibiapp.habibi.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.habibiapp.habibi.adapters.PhraseAdapter;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.datasources.PhraseDataSource;
import com.habibiapp.habibi.models.Category;
import com.habibiapp.habibi.models.Language;
import com.habibiapp.habibi.models.Phrase;

import java.util.List;

/**
* Created by habibi on 5/8/14.
*/
public class ViewPhrasesFragment extends Fragment {
    public static String TAG = ViewPhrasesFragment.class.getSimpleName();
    public static String CATEGORY_KEY = "category";
    private Category category;

    public static ViewPhrasesFragment newInstance(Category category) {
        ViewPhrasesFragment fragment = new ViewPhrasesFragment();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        category = getArguments().getParcelable(CATEGORY_KEY);

        View view = inflater.inflate(R.layout.fragment_view_phrases, container, false);
        ListView listView = (ListView) view.findViewById(R.id.phrases_listview);

        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        List<Phrase> phrases;
        if (Category.ALL.equals(category)) {
            phrases = phraseDataSource.getPhrases(-1, null, null, null, Language.ENGLISH, null);
        } else {
            phrases = phraseDataSource.getPhrases(-1, category, null, null, Language.ENGLISH, null);
        }

        phraseDataSource.close();
        PhraseAdapter phraseAdapter = new PhraseAdapter(getActivity(), R.id.category_view_text, phrases, category);
        listView.setAdapter(phraseAdapter);
        return view;
    }
}
