package com.codingcamels.habibitime;

import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;

/**
 * Created by samsoom on 3/29/15.
 */
public class PhraseBuilder {
    private  Phrase phrase;

    public static PhraseBuilder cratePhrase() {
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
        phrase.setNativePhraseSpelling(properSpelling);
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
        return phrase;
    }
}

// PhraseBuilder.createNew().setPhrase("hala").setPhonetic("niiii").set
