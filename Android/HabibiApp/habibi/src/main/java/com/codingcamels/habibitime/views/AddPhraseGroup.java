package com.codingcamels.habibitime.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.codingcamels.habibitime.models.Phrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsoom on 3/30/15.
 */
public class AddPhraseGroup extends View {

    private List<AddPhrase> phrases = new ArrayList<AddPhrase>();
    private AddPhrase.AddPhraseListener addPhraseListener;

    public AddPhraseGroup(final Context context) {
        super(context);
    }

    public AddPhraseGroup(final Context context, AddPhrase.AddPhraseListener addPhraseListener, final Type type, final ViewGroup viewGroup) {
        super(context);
        this.addPhraseListener = addPhraseListener;
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
        AddPhrase phrase = new AddPhrase(getContext(), addPhraseListener, type, viewGroup);
        phrases.add(phrase);
        viewGroup.addView(phrase);
    }

    public List<Phrase> getPhrases() {
        List<Phrase> phrasesList = new ArrayList<Phrase>();
        for (AddPhrase addPhrase : phrases) {
            phrasesList.addAll(addPhrase.getPhrases());
        }
        return phrasesList;
    }

    public enum Type {
        ALL, TO_ALL, FROM_ALL, NO_GENDER;
    }

}
