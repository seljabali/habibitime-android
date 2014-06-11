package com.habibiapp.habibi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samsoom on 4/9/14.
 */
public class EnglishPhrase implements Parcelable {
    private String phrase;

    public EnglishPhrase (String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }


    protected EnglishPhrase(Parcel in) {
        phrase = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phrase);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<EnglishPhrase> CREATOR = new Parcelable.Creator<EnglishPhrase>() {
        @Override
        public EnglishPhrase createFromParcel(Parcel in) {
            return new EnglishPhrase(in);
        }

        @Override
        public EnglishPhrase[] newArray(int size) {
            return new EnglishPhrase[size];
        }
    };
}