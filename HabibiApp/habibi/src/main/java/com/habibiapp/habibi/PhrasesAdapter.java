package com.habibiapp.habibi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.habibiapp.habibi.models.HabibiPhrase;
import java.util.List;

/**
 * Created by habibi on 5/8/14.
 */
public class PhrasesAdapter extends ArrayAdapter<HabibiPhrase> {
    private MainActivity mainActivity;

    public PhrasesAdapter(Context context, List<HabibiPhrase> users) {
        super(context, R.layout.view_phrase, users);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HabibiPhrase habibiPhrase = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_phrase, parent, false);
        }

        TextView textViewPhrase = (TextView) convertView.findViewById(R.id.phrase_view_text);
        textViewPhrase.setText(habibiPhrase.getEnglishPhrase().getPhrase());
        textViewPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.launchPhraseViewFragment(habibiPhrase);
            }
        });

        return convertView;
    }

}