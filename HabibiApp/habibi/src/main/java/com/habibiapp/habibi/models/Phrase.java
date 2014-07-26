package com.habibiapp.habibi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samsoom on 4/9/14.
 */
public class Phrase implements Parcelable {
    private int id;
    private int habibiPhraseId;
    private Language language;
    private Dialect dialect;
    private Gender fromGender;
    private Gender toGender;
    private String nativePhraseSpelling;
    private String phoneticPhraseSpelling;
    private String properPhoneticPhraseSpelling;

    public Phrase () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHabibiPhraseId() {
        return habibiPhraseId;
    }

    public void setHabibiPhraseId(int id) {
        this.habibiPhraseId = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = Language.getLanguageFromColumn(language);
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(int dialect) {
        this.dialect = Dialect.getLanguageFromColumn(dialect);
    }

    public Gender getFromGender() {
        return this.fromGender;
    }

    public void setFromGender(int fromGender) {
        this.fromGender = Gender.getGenderFromColumn(fromGender);
    }

    public Gender getToGender() {
        return toGender;
    }

    public void setToGender(int toGender) {
        this.toGender = Gender.getGenderFromColumn(toGender);;
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

    public void setProperPhoneticPhraseSpelling(String properPhoneticPhraseSpelling) {
        this.properPhoneticPhraseSpelling = properPhoneticPhraseSpelling;
    }

    protected Phrase(Parcel in) {
        id = in.readInt();
        habibiPhraseId = in.readInt();
        language = (Language) in.readValue(Language.class.getClassLoader());
        dialect = (Dialect) in.readValue(Dialect.class.getClassLoader());
        fromGender = (Gender) in.readValue(Gender.class.getClassLoader());
        toGender = (Gender) in.readValue(Gender.class.getClassLoader());
        nativePhraseSpelling = in.readString();
        phoneticPhraseSpelling = in.readString();
        properPhoneticPhraseSpelling = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(habibiPhraseId);
        dest.writeValue(language);
        dest.writeValue(dialect);
        dest.writeValue(fromGender);
        dest.writeValue(toGender);
        dest.writeString(nativePhraseSpelling);
        dest.writeString(phoneticPhraseSpelling);
        dest.writeString(properPhoneticPhraseSpelling);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Phrase> CREATOR = new Parcelable.Creator<Phrase>() {
        @Override
        public Phrase createFromParcel(Parcel in) {
            return new Phrase(in);
        }

        @Override
        public Phrase[] newArray(int size) {
            return new Phrase[size];
        }
    };
}