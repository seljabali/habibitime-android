package com.codingcamels.habibitime.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samsoom on 4/9/14.
 */
public class HabibiPhrase implements Parcelable {
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


    protected HabibiPhrase(Parcel in) {
        id = in.readLong();
        category = (Category) in.readValue(Category.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HabibiPhrase> CREATOR = new Parcelable.Creator<HabibiPhrase>() {
        @Override
        public HabibiPhrase createFromParcel(Parcel in) {
            return new HabibiPhrase(in);
        }

        @Override
        public HabibiPhrase[] newArray(int size) {
            return new HabibiPhrase[size];
        }
    };
}