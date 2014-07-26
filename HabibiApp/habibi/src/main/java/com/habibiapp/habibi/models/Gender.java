package com.habibiapp.habibi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.habibiapp.habibi.datasources.GenderDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Gender implements Parcelable {
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

    public String getGenderNameShortened() {
        if (Gender.MALE.equals(this)) {
            return "m";
        } else {
            return "f";
        }
    }

    public int getId() {
        return genderId;
    }

    public void setGenderId(int id) {
        this.genderId = id;
    }

    public static Gender getGenderFromID(String id) {
        return "1".equals(id) ? MALE : FEMALE;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Gender) {
            if (this.getId() == ((Gender) object).getId()) {
                return true;
            }
        }
        return false;
    }

    protected Gender(Parcel in) {
        genderId = in.readInt();
        genderName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(genderId);
        dest.writeString(genderName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Gender> CREATOR = new Parcelable.Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel in) {
            return new Gender(in);
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[size];
        }
    };
}