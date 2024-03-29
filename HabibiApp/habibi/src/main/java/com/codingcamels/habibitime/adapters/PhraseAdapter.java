package com.codingcamels.habibitime.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.utilities.ViewUtils;
import com.codingcamels.habibitime.fragments.ViewPhraseFragment;
import com.codingcamels.habibitime.fragments.ViewPhrasesFragment;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Phrase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 6/22/14.
 */
public class PhraseAdapter extends ArrayAdapter<Phrase> {
    private static final int PHRASE_COUNT_MAX = 13;

    HashMap<Phrase, Integer> idMap = new HashMap<Phrase, Integer>();
    private int phraseCount;
    private Activity activity;
    private Category category;
    private TextView itemView;

    public PhraseAdapter(Context context, int resource, List<Phrase> phrases, Category category) {
        super(context, resource, phrases);
        this.activity = (Activity)context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_phrase_item, null);
        }

        final Phrase phrase = getItem(position);
        if (phrase!= null) {
            itemView = (TextView) view.findViewById(R.id.phrase_view_text);
            if (itemView != null) {
                final View superView = view;
                itemView.setText(phrase.getNativePhraseSpelling());
                itemView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                init(phrase, position, superView);
                            }
                        }
                );
            }
        }
        int dpHeight;
        if (phraseCount <= PHRASE_COUNT_MAX) {
            dpHeight = (int) ViewUtils.getScreenHeightDP(activity) / phraseCount;
        } else {
            dpHeight = (int) activity.getResources().getDimension(R.dimen.phrase_adapter_item_height);
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.phrase_view_layout);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpHeight));
        return view;
    }

    private int getColorForPhrase(int position) {
        TypedArray colors = activity.getResources().obtainTypedArray(R.array.habibi_colors);
        return colors.getColor(position % colors.length(), 0);
    }

    private void init(Phrase phrase, int position, View view) {
        view.setBackgroundColor(getColorForPhrase(position));
        ViewPhraseFragment fragment = ViewPhraseFragment.newInstance(phrase, category);
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit)
                .replace(R.id.fragmentLayoutContainer, fragment, ViewPhraseFragment.TAG)
                .addToBackStack(ViewPhrasesFragment.TAG)
                .commit();
    }

}
