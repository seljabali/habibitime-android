package com.habibiapp.habibi.models;


import com.habibiapp.habibi.datasources.DialectDataSource;

/**
 * Created by habibi on 6/8/14.
 */
public class Dialect {
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
}
