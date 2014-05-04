package com.habibiapp.habibi;

import com.habibiapp.habibi.models.*;

/**
 * Created by samsoom on 4/13/14.
 */
public class EnglishPhraseBuilder {

    private EnglishPhrase englishPhrase;

    public EnglishPhraseBuilder() {}

    public EnglishPhraseBuilder buildEnglishPhrase(String phrase) {
        englishPhrase = new EnglishPhrase(phrase);
        return this;
    }

    public EnglishPhraseBuilder addArabicPhrase(String phrase) {
        englishPhrase.setArabicPhrase(new ArabicPhrase(phrase));
        return this;
    }

    public EnglishPhraseBuilder addArabiziPhrase(String phrase) {
        englishPhrase.setArabiziPhrase(new ArabiziPhrase(phrase));
        return this;
    }

    public EnglishPhraseBuilder addProperBiziPhrase(String phrase) {
        englishPhrase.setProperBiziPhrase(new ProperBiziPhrase(phrase));
        return this;
    }

    public EnglishPhraseBuilder addFromPerson(EnglishPhrase.Person person) {
        englishPhrase.setAdresserPerson(person);
        return this;
    }

    public EnglishPhraseBuilder addToPerson(EnglishPhrase.Person person) {
        englishPhrase.setAdresseePerson(person);
        return this;
    }

    public EnglishPhraseBuilder addDialect(EnglishPhrase.Dialect dialect) {
        englishPhrase.setDialect(dialect);
        return this;
    }

    public EnglishPhrase build() {
        return englishPhrase;
    }
}
