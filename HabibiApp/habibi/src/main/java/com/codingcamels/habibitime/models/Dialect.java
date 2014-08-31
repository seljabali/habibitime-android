package com.codingcamels.habibitime.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.codingcamels.habibitime.datasources.DialectDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Dialect implements Parcelable {
    public static final Dialect JORDAN = new Dialect("Jordan");

    private int id;
    private String dialectName;

    public Dialect(String dialectName) {
        this.dialectName = dialectName;
    }

    public static Dialect getLanguageFromColumn(int column) {
        if (column == DialectDataSource.JORDAN) {
            return JORDAN;
        }
        return null;
    }

    public int getId() {
        return id;
    }
    public String getDialectName() {
        return dialectName;
    }

    protected Dialect(Parcel in) {
        id = in.readInt();
        dialectName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(dialectName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Dialect> CREATOR = new Parcelable.Creator<Dialect>() {
        @Override
        public Dialect createFromParcel(Parcel in) {
            return new Dialect(in);
        }

        @Override
        public Dialect[] newArray(int size) {
            return new Dialect[size];
        }
    };
}