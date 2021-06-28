package com.codingcamels.habibitime.bibi;

import android.widget.ArrayAdapter;

import com.codingcamels.habibitime.models.Phrase;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Category;

import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 8/10/14.
 */
public class PhrasePopUpAdapter extends ArrayAdapter<Phrase> {
    HashMap<Phrase, Integer> idMap = new HashMap<Phrase, Integer>();
    private int phraseCount;
    private Context context;
    private Category category;

    public PhrasePopUpAdapter(Context context, int resource, List<Phrase> phrases, Category category) {
        super(context, resource, phrases);
        this.context = context;
        this.phraseCount = phrases.size();
        this.category = category;
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
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_phrase_item, null);
        }

        final Phrase phrase = getItem(position);
        if (phrase!= null) {
            TextView itemView = (TextView) view.findViewById(R.id.phrase_view_text);
            if (itemView != null) {
                itemView.setText(phrase.getNativePhraseSpelling());
                itemView.setBackgroundColor(getColorForPhrase(position));
            }
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.phrase_view_layout);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return view;
    }

    private int getColorForPhrase(int position) {
        TypedArray colors = context.getResources().obtainTypedArray(R.array.habibi_colors_short);
        return colors.getColor(position % colors.length(), 0);
    }

}
