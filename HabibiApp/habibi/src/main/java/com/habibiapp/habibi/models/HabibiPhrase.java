package com.habibiapp.habibi.models;

/**
 * Created by samsoom on 4/9/14.
 */
public class HabibiPhrase {
    private long id;
    private Category category;

    public HabibiPhrase() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = Category.getCategoryFromColumn(category);
    }

}
