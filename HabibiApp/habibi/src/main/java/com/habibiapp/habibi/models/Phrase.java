package com.habibiapp.habibi.models;

/**
 * Created by samsoom on 4/9/14.
 */
public class Phrase {
    private int language;
    private int dialect;
    private int fromGender;
    private int toGender;
    private String nativePhraseSpelling;
    private String phoneticPhraseSpelling;
    private String properPhoneticPhraseSpelling;

    public Phrase () {}

    public int getLanguage() {
        return language;
    }
    public void setLanguage(int language) {
        this.language = language;
    }

    public int getDialect() {
        return dialect;
    }
    public void setDialect(int dialect) {
        this.dialect = dialect;
    }

    public int getFromGender() {
        return this.fromGender;
    }
    public void setFromGender(int fromGender) {
        this.fromGender = fromGender;
    }

    public int getToGender() {
        return toGender;
    }
    public void setToGender(int toGender) {
        this.toGender = toGender;
    }

    public String getNativePhraseSpelling() {
        return nativePhraseSpelling;
    }
    public void setNativePhraseSpelling(String nativePhraseSpelling) {
        this.nativePhraseSpelling = nativePhraseSpelling;
    }

    public String getPhoneticPhraseSpelling() {
        return phoneticPhraseSpelling;
    }
    public void setPhoneticPhraseSpelling(String phoneticPhraseSpelling) {
        this.phoneticPhraseSpelling = phoneticPhraseSpelling;
    }

    public String getProperPhoneticPhraseSpelling() {
        return properPhoneticPhraseSpelling;
    }
    public void setProperPhoneticPhraseSpelling(String ProperPhoneticPhraseSpelling) {
        this.properPhoneticPhraseSpelling = properPhoneticPhraseSpelling;
    }

}