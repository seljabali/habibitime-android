package com.codingcamels.habibitime.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codingcamels.habibitime.adapters.PhraseAdapter;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;

import java.util.List;

/**
* Created by habibi on 5/8/14.
*/
public class ViewPhrasesFragment extends Fragment {
    public static String TAG = ViewPhrasesFragment.class.getSimpleName();
    public static String CATEGORY_KEY = "category";
    private Category category;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(false);
    }

    public static ViewPhrasesFragment newInstance(Category category) {
        ViewPhrasesFragment fragment = new ViewPhrasesFragment();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_phrases, container, false);
        if (view == null) {
            return null;
        }
        listView = (ListView) view.findViewById(R.id.phrases_listview);
        category = getArguments().getParcelable(CATEGORY_KEY);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        List<Phrase> phrases;
        if (Category.SETTINGS.equals(category)) {
            phrases = phraseDataSource.getPhrases(-1, null, null, null, Language.ENGLISH, null);
        } else {
            phrases = phraseDataSource.getPhrases(-1, category, null, null, Language.ENGLISH, null);
        }
        phraseDataSource.close();
        PhraseAdapter phraseAdapter = new PhraseAdapter(getActivity(), R.id.category_view_text, phrases, category);
        listView.setAdapter(phraseAdapter);
    }
}
