package com.codingcamels.habibitime.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.codingcamels.habibitime.datasources.LanguageDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Language implements Parcelable {
    public static final Language ARABIC = new Language(1, "Arabic");
    public static final Language ENGLISH = new Language(2, "English");

    private int languageId;
    private String languageName;

    public Language(int id, String languageName) {
        this.languageId = id;
        this.languageName = languageName;
    }

    public static Language getLanguageFromColumn(int column) {
        if (column == LanguageDataSource.ARABIC) {
            return ARABIC;
        }
        if (column == LanguageDataSource.ENGLISH) {
            return ENGLISH;
        }
        return null;
    }

    public String getLanguageName() {
        return languageName;
    }

    public int getId() {
        return languageId;
    }

    public void setLanguageId(int id) {
        this.languageId = id;
    }

    protected Language(Parcel in) {
        languageId = in.readInt();
        languageName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(languageId);
        dest.writeString(languageName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
}