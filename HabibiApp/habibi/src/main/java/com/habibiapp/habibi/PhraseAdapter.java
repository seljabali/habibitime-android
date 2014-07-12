package com.habibiapp.habibi;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habibiapp.habibi.fragments.ViewPhraseFragment;
import com.habibiapp.habibi.fragments.ViewPhrasesFragment;
import com.habibiapp.habibi.models.Category;
import com.habibiapp.habibi.models.Phrase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 6/22/14.
 */
public class PhraseAdapter extends ArrayAdapter<Phrase> {

    HashMap<Phrase, Integer> idMap = new HashMap<Phrase, Integer>();
    private int phraseCount;
    private Activity activity;

    public PhraseAdapter(Context context, int resource, List<Phrase> phrases) {
        super(context, resource, phrases);
        this.activity = (Activity)context;
        this.phraseCount = phrases.size();
        for (int i = 0; i < phrases.size(); i++) {
            idMap.put(phrases.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        Phrase item = getItem(position);
        return idMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_phrase, null);
        }

        final Phrase phrase = getItem(position);
        if (phrase!= null) {
            TextView itemView = (TextView) view.findViewById(R.id.phrase_view_text);
            if (itemView != null) {
                itemView.setText(phrase.getNativePhraseSpelling());
                itemView.setBackgroundColor(getColorForPhrase(position));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewPhraseFragment fragment = ViewPhraseFragment.newInstance(activity, phrase);
                        activity.getFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayoutContainer, fragment, ViewPhraseFragment.TAG)
                                .addToBackStack(ViewPhrasesFragment.TAG)
                                .commit();
                    }
                });
            }
        }
        int dpHeight = (int) ViewUtil.getScreenHeightDP(activity) / (phraseCount - 1);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.phrase_view_layout);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpHeight));
        return view;
    }

    private int getColorForPhrase(int position) {
        TypedArray colors = activity.getResources().obtainTypedArray(R.array.habibi_colors);
        return colors.getColor(position % colors.length(), 0);
    }
}