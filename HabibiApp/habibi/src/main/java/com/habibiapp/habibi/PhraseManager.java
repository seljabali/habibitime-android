package com.habibiapp.habibi;

import android.app.Activity;
import android.content.res.Resources;

import com.habibiapp.habibi.models.EnglishPhrase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsoom on 4/9/14.
 */
public class PhraseManager {

    private List<EnglishPhrase> flirtI = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> flirtU = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> flirtQ = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> meetupI = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> meetupU = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> meetupQ = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> moodI = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> moodU = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> moodQ = new ArrayList<EnglishPhrase>();
    private List<EnglishPhrase> answers = new ArrayList<EnglishPhrase>();
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
        if (line.length != 2 || line.length != 9) return;
        EnglishPhraseBuilder builder = new EnglishPhraseBuilder();
        EnglishPhrase englishPhrase = builder.buildEnglishPhrase(line[ENGLISH_PHRASE_POSITION])
                                        .addArabicPhrase(line[ARABIC_PHRASE_POSITION])
                                        .addArabiziPhrase(line[ARABIZI_PHRASE_POSITION])
                                        .addProperBiziPhrase(line[PROPERBIZI_PHRASE_POSITION])
                                        .addToPerson(EnglishPhrase.Person.valueOf(line[TO_GENDER_POSITION]))
                                        .addFromPerson(EnglishPhrase.Person.valueOf(line[FROM_GENDER_POSITION]))
                                        .addDialect(EnglishPhrase.Dialect.valueOf(line[DIALECT_POSITION]))
                                        .build();


        switch ((EnglishPhrase.CategoryFirst)englishPhrase.getCategoryFirst()) {
            EnglishPhrase.CategoryFirst.Flirt: {
                ;
            }
        }
        if (englishPhrase.isEqualFirstCategory(EnglishPhrase.CategoryFirst.Flirt)) {
            if (englishPhrase.isEqualSecondCategory(EnglishPhrase.CategorySecond.I))
        }
        flirtI
        flirtU
        flirtQ
        meetupI
        meetupU
        meetupQ
        moodI
        moodU
        moodQ
        answers

        switch (line[FIRST_CATEGORY_POSITION]) {
            case "Flirt":
                switch (line[SECOND_CATEGORY_POSITION]) {
                    case "I":
                        flirtI.add();
                }
            case "F":
                answers.add(new EnglishPhrase(line[1], EnglishPhrase.CategoryFirst.Answers));
                break;
            case 9:
                answers.add(new EnglishPhrase(line[1], EnglishPhrase.CategoryFirst.Answers));
                break;
            default:
                break;
        }
    }
//        Flirt, I, I admire you, Ana mo'jabeh feek, Ana mo3jabeh feek, , f, m, all
}
