package com.habibiapp.habibi.models;

/**
 * Created by samsoom on 4/9/14.
 */
public class HabibiPhrase extends Phrase {
    private long id;
    private int popularity;

    public HabibiPhrase() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity= popularity;
    }
}
