package com.habibiapp.habibi.models;

import com.habibiapp.habibi.datasources.GenderDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Gender {
    public static final Gender MALE = new Gender(1, "Male");
    public static final Gender FEMALE = new Gender(2, "Female");

    private int genderId;
    private String genderName;

    public Gender(int id, String gender) {
        this.genderId = id;
        this.genderName = gender;
    }

    public static Gender getGenderFromColumn(int column) {
        if (column == GenderDataSource.MALE) {
            return MALE;
        }
        if (column == GenderDataSource.FEMALE) {
            return FEMALE;
        }
        return null;
    }

    public String getGenderName() {
        return genderName;
    }

    public int getId() {
        return genderId;
    }

    public void setGenderId(int id) {
        this.genderId = id;
    }
}
