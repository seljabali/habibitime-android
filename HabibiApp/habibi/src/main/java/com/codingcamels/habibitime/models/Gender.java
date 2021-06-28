package com.codingcamels.habibitime.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.codingcamels.habibitime.datasources.GenderDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Gender implements Parcelable {
    public static final Gender MALE = new Gender(1, "Male");
    public static final Gender FEMALE = new Gender(2, "Female");
    public static final Gender NONE = new Gender(-1, "Genderless");

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
        } else if (Gender.FEMALE.equals(this)) {
            return "f";
        } else {
            return "na";
        }
    }

    public int getId() {
        return genderId;
    }
    public String getIdAsString() {
        return Integer.toString(genderId);
    }

    public void setGenderId(int id) {
        this.genderId = id;
    }

    public static Gender getGenderFromID(String id) {
        if ("1".equals(id)) {
            return MALE;
        } else if ("2".equals(id)) {
            return FEMALE;
        } else {
            return NONE;
        }
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