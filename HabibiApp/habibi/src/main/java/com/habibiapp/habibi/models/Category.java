package com.habibiapp.habibi.models;

import android.util.Log;

import com.habibiapp.habibi.datasources.CategoryDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Category {
    public static final Category MOOD = new Category(CategoryDataSource.MOOD, "Mood");
    public static final Category QUESTION = new Category(CategoryDataSource.QUESTION, "Questions");
    public static final Category ANSWER = new Category(CategoryDataSource.ANSWER, "Responses");
    public static final Category FLIRT = new Category(CategoryDataSource.FLIRT, "Flirt");

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
}
