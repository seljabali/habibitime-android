package com.habibiapp.habibi.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.habibiapp.habibi.datasources.CategoryDataSource;

import java.util.Objects;

/**
 * Created by habibi on 6/8/14.
 */
public class Category implements Parcelable {
    public static final Category MOOD = new Category(CategoryDataSource.MOOD, "Mood");
    public static final Category QUESTION = new Category(CategoryDataSource.QUESTION, "Questions");
    public static final Category ANSWER = new Category(CategoryDataSource.ANSWER, "Responses");
    public static final Category FLIRT = new Category(CategoryDataSource.FLIRT, "Flirt");
    public static final int CATEGORY_COUNT = 4;

    private int id;
    private String categoryName;

    public Category() {}

    public Category(int id, String name) {
        this.id = id;
        this.categoryName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public static Category getCategoryFromColumn(int categoryId) {
        switch (categoryId) {
            case CategoryDataSource.MOOD:
                return MOOD;
            case CategoryDataSource.QUESTION:
                return QUESTION;
            case CategoryDataSource.ANSWER:
                return ANSWER;
            case CategoryDataSource.FLIRT:
                return FLIRT;
            default:
                Log.e("Category", "Couldn't getCategoryFromColumn()");
                return null;
        }
    }

    public static Category getCategoryFromName(String name) {
        if (MOOD.getCategoryName().equals(name)) {
            return MOOD;
        }
        if (QUESTION.getCategoryName().equals(name)) {
            return QUESTION;
        }
        if (ANSWER.getCategoryName().equals(name)) {
            return ANSWER;
        }
        if (FLIRT.getCategoryName().equals(name)) {
            return FLIRT;
        }
        Log.e("Category", "Couldn't find category: " + name);
        return null;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Category) {
            if (this.getId() == ((Category) object).getId()) {
                return true;
            }
        }
        return false;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        categoryName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(categoryName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
