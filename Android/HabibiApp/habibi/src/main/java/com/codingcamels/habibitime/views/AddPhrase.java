package com.codingcamels.habibitime.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codingcamels.habibitime.R;

/**
 * Created by samsoom on 4/1/15.
 */
public class AddPhrase extends View {

    private static final String TAG = AddPhrase.class.getSimpleName();

    public AddPhrase(Context context, Type type, ViewGroup viewGroup) {
        super(context);
        init(type, viewGroup);
    }

    private void init(Type type, ViewGroup viewGroup) {
        View view = LayoutInflater.from(getContext()).cloneInContext(getContext()).inflate(R.layout.view_add_arabic_phrase, viewGroup, true);
        TextView titleView = (TextView) view.findViewById(R.id.fromGenderToGenderTitle);
        TextView phoneticTitleView = (TextView) view.findViewById(R.id.phoneticEditText);
        TextView arabiziTitleView = (TextView) view.findViewById(R.id.arabiziEditText);
        Button playBack = (Button) view.findViewById(R.id.play_back);
        Button record = (Button) view.findViewById(R.id.record);

        switch (type) {
            case MtoF:
                titleView.setText("M->F");
                break;
            case FtoM:
                titleView.setText("F->M");
                break;
            case MtoM:
                titleView.setText("M->M");
                break;
            case FtoF:
                titleView.setText("F->F");
                break;
            case MtoF_FtoF:
                titleView.setText("M->F, F->F");
                break;
            case FtoM_MtoM:
                titleView.setText("F->M, M->M");
                break;
            case FtoM_FtoF:
                titleView.setText("F->M, F->F");
                break;
            case MtoF_MtoM:
                titleView.setText("M->F, M->M");
                break;
            case Genderless:
                titleView.setText("Genderless");
                break;
            default:
                Log.e(TAG, "Invalid AddPhrase Type!");
        }

        titleView.setId(-1);
        playBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public enum Type {
        MtoF, FtoM, MtoM, FtoF, MtoF_FtoF, FtoM_MtoM, MtoF_MtoM, FtoM_FtoF, Genderless;
    }
}
