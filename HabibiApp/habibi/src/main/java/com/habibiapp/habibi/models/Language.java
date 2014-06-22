package com.habibiapp.habibi.models;

import com.habibiapp.habibi.datasources.LanguageDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Language {
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
}
