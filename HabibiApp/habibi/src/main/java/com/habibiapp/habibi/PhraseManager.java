package com.habibiapp.habibi;

import android.app.Activity;
import android.content.res.Resources;

import com.habibiapp.habibi.models.EnglishPhrase;
import com.habibiapp.habibi.models.HabibiPhrase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsoom on 4/9/14.
 */
public class PhraseManager {

    private List<HabibiPhrase> phrases = new ArrayList<HabibiPhrase>();
    private Activity activity;
    private final int FIRST_CATEGORY_POSITION = 0;
    private final int SECOND_CATEGORY_POSITION = 1;
    private final int ENGLISH_PHRASE_POSITION = 2;
    private final int ARABIZI_PHRASE_POSITION = 3;
    private final int PROPERBIZI_PHRASE_POSITION = 4;
    private final int ARABIC_PHRASE_POSITION = 5;
    private final int FROM_GENDER_POSITION = 6;
    private final int TO_GENDER_POSITION = 7;
    private final int DIALECT_POSITION = 8;

    public PhraseManager(Activity activity) {
        this.activity = activity;
        loadPhrases(R.raw.dictionary_small);
    }

    public PhraseManager(Activity activity, int dictionaryId) {
        this.activity = activity;
        loadPhrases(dictionaryId);
    }

    private void loadPhrases(int dictionaryId) {
        try {
            Resources resources = activity.getResources();
            InputStream inputStream = resources.openRawResource(dictionaryId);
            InputStreamReader inputreader = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;

            do {
                line = buffreader.readLine();
                addPhrase(line);
            } while (line != null);

        } catch (Exception e) {
            ;
        }
    }

    private void addPhrase(String csvLine){
        String[] line = csvLine.split(", ");
        PhraseBuilder builder = new PhraseBuilder();
        HabibiPhrase habibiPhrase = builder.addEnglishPhrase(line[ENGLISH_PHRASE_POSITION])
                                        .addArabicPhrase(line[ARABIC_PHRASE_POSITION])
                                        .addArabiziPhrase(line[ARABIZI_PHRASE_POSITION])
                                        .addProperBiziPhrase(line[PROPERBIZI_PHRASE_POSITION])
                                        .addToPerson(HabibiPhrase.Person.valueOf(line[TO_GENDER_POSITION]))
                                        .addFromPerson(HabibiPhrase.Person.valueOf(line[FROM_GENDER_POSITION]))
                                        .addFirstCategory(HabibiPhrase.CategoryFirst.valueOf(line[FIRST_CATEGORY_POSITION]))
                                        .addSecondCategory(HabibiPhrase.CategorySecond.valueOf(line[SECOND_CATEGORY_POSITION]))
                                        .build();

        phrases.add(habibiPhrase);
    }

    public List<HabibiPhrase> getPhrases(HabibiPhrase.CategoryFirst categoryFirst) {
        if (categoryFirst == null) {
            return new ArrayList<HabibiPhrase>();
        }
        List<HabibiPhrase> results = new ArrayList<HabibiPhrase>();
        for (int i = 0; i < phrases.size(); i++) {
            if (phrases.get(i).isEqualFirstCategory(categoryFirst)) {
                results.add(phrases.get(i));
            }
        }
        return results;
    }


    public List<HabibiPhrase> getPhrases(HabibiPhrase.CategoryFirst categoryFirst, HabibiPhrase.CategorySecond categorySecond) {
        if (categoryFirst == null) {
            return new ArrayList<HabibiPhrase>();
        }
        List<HabibiPhrase> results = new ArrayList<HabibiPhrase>();
        for (int i = 0; i < phrases.size(); i++) {
            if (phrases.get(i).isEqualFirstCategory(categoryFirst) && phrases.get(i).isEqualSecondCategory(categorySecond)) {
                results.add(phrases.get(i));
            }
        }
        return results;
    }
//        Flirt, I, I admire you, Ana mo'jabeh feek, Ana mo3jabeh feek, , f, m, all
}
