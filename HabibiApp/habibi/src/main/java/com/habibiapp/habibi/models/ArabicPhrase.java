package com.habibiapp.habibi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samsoom on 4/9/14.
 */
public class ArabicPhrase implements Parcelable {
    private String phrase;
    private String phraseArabizi;
    private String phraseProperbizi;

    public ArabicPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhraseArabizi() {
        return phraseArabizi;
    }

    public void setPhraseArabizi(String phraseArabizi) {
        this.phraseArabizi = phraseArabizi;
    }

    public String getPhraseProperBizi() {
        return phraseProperbizi;
    }

    public void setPhraseProperbizi(String phraseProperbizi) {
        this.phraseProperbizi = phraseProperbizi;
    }

    protected ArabicPhrase(Parcel in) {
        phrase = in.readString();
        phraseArabizi = in.readString();
        phraseProperbizi = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phrase);
        dest.writeString(phraseArabizi);
        dest.writeString(phraseProperbizi);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ArabicPhrase> CREATOR = new Parcelable.Creator<ArabicPhrase>() {
        @Override
        public ArabicPhrase createFromParcel(Parcel in) {
            return new ArabicPhrase(in);
        }

        @Override
        public ArabicPhrase[] newArray(int size) {
            return new ArabicPhrase[size];
        }
    };

}
