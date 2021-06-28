package com.codingcamels.habibitime;

import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;

/**
 * Created by samsoom on 3/29/15.
 */
public class PhraseBuilder {
    private  Phrase phrase;

    public static PhraseBuilder createPhrase() {
        PhraseBuilder phraseBuilder = new PhraseBuilder();
        return phraseBuilder;
    }

    public PhraseBuilder() {
        this.phrase = new Phrase();
    }

    public PhraseBuilder setNativeSpelling(String nativeSpelling) {
        phrase.setNativePhraseSpelling(nativeSpelling);
        return this;
    }

    public PhraseBuilder setPhoneticSpelling(String phoneticSpelling) {
        phrase.setPhoneticPhraseSpelling(phoneticSpelling);
        return this;
    }


    public PhraseBuilder setProperSpelling(String properSpelling) {
        phrase.setProperPhoneticPhraseSpelling(properSpelling);
        return this;
    }

    public PhraseBuilder setLanguage(Language language) {
        phrase.setLanguage(language);
        return this;
    }

    public PhraseBuilder setFromGender(Gender fromGender) {
        phrase.setFromGender(fromGender);
        return this;
    }

    public PhraseBuilder setToGender(Gender toGender) {
        phrase.setToGender(toGender);
        return this;
    }

    public Phrase build() {
        phrase.setSoundFileName(getFileName());
        return phrase;
    }

    public String getFileName() {
        return phrase.getProperPhoneticPhraseSpelling().replace(" ", "_") + "_" +
                phrase.getLanguage().getLanguageName() + "_" +
                phrase.getFromGender().getGenderNameShortened() + "_" +
                phrase.getToGender().getGenderNameShortened() + "_";
    }
}