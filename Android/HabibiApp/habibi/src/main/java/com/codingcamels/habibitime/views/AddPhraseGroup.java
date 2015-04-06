package com.codingcamels.habibitime.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samsoom on 3/30/15.
 */
public class AddPhraseGroup extends View {

    public AddPhraseGroup(final Context context, final Type type, final ViewGroup viewGroup) {
        super(context);
        init(type, viewGroup);
    }

    private void init(Type type, ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        switch (type) {
            case ALL:
                addPhrase(AddPhrase.Type.MtoF, viewGroup);
                addPhrase(AddPhrase.Type.FtoM, viewGroup);
                addPhrase(AddPhrase.Type.MtoM, viewGroup);
                addPhrase(AddPhrase.Type.FtoF, viewGroup);
                break;
            case TO_ALL:
                addPhrase(AddPhrase.Type.MtoF_FtoF, viewGroup);
                addPhrase(AddPhrase.Type.FtoM_MtoM, viewGroup);
                break;
            case FROM_ALL:
                addPhrase(AddPhrase.Type.FtoM_MtoM, viewGroup);
                addPhrase(AddPhrase.Type.MtoF_FtoF, viewGroup);
                break;
            case NO_GENDER:
                addPhrase(AddPhrase.Type.Genderless, viewGroup);
                break;
            default:
                Log.e("asd", "asd");
        }

    }

    private void addPhrase(AddPhrase.Type type, ViewGroup viewGroup) {
        AddPhrase phrase = new AddPhrase(getContext(), type, viewGroup);
        viewGroup.addView(phrase);
    }

    public enum Type {
        ALL, TO_ALL, FROM_ALL, NO_GENDER;
    }

}
