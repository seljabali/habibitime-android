package com.habibiapp.habibi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samsoom on 4/9/14.
 */
public class HabibiPhrase implements Parcelable {
    private ArabicPhrase arabicPhrase;
    private EnglishPhrase englishPhrase;
    private CategoryFirst categoryFirst;
    private CategorySecond categorySecond;
    private Person fromPerson;
    private Person toPerson;


    public HabibiPhrase() {}

    public enum CategoryFirst {
        Flirt, Meetup, Mood, Answers
    }

    public enum CategorySecond {
        I, You, Question
    }

    public enum Person {
        All, Male, Female, MalePlural, FemalePlural
    }

    public String getArabicPhrase() {
        return arabicPhrase.getPhrase();
    }

    public void setArabicPhrase(ArabicPhrase arabicPhrase) {
        this.arabicPhrase = arabicPhrase;
    }

    public ArabicPhrase getArabicPhraseObject() {
        return arabicPhrase;
    }

    public EnglishPhrase getEnglishPhrase() {
        return englishPhrase;
    }

    public void setEnglishPhrase(EnglishPhrase englishPhrase) {
        this.englishPhrase = englishPhrase;
    }

    public CategoryFirst getCategoryFirst() {
        return this.categoryFirst;
    }

    public CategorySecond getCategorySecond() {
        return this.categorySecond;
    }

    public void setCategoryFirst(CategoryFirst categoryFirst) {
        this.categoryFirst = categoryFirst;
    }

    public void setCategorySecond(CategorySecond categorySecond) {
        this.categorySecond = categorySecond;
    }

    public void setFromPerson(Person fromPerson) {
        this.fromPerson = fromPerson;
    }

    public void setToPerson(Person toPerson) {
        this.toPerson = toPerson;
    }

    public boolean isEqualFirstCategory(CategoryFirst categoryFirst) {
        return this.categoryFirst == categoryFirst;
    }

    public boolean isEqualSecondCategory(CategorySecond categorySecond) {
        return this.categorySecond == categorySecond;
    }

    public boolean isEqualFromGender(Person fromGender) {
        return this.fromPerson == fromGender;
    }

    public boolean isEqualToGender(Person toGender) {
        return this.toPerson == toGender; }

    protected HabibiPhrase(Parcel in) {
        arabicPhrase = (ArabicPhrase) in.readValue(ArabicPhrase.class.getClassLoader());
        englishPhrase = (EnglishPhrase) in.readValue(EnglishPhrase.class.getClassLoader());
        categoryFirst = (CategoryFirst) in.readValue(CategoryFirst.class.getClassLoader());
        categorySecond = (CategorySecond) in.readValue(CategorySecond.class.getClassLoader());
        fromPerson = (Person) in.readValue(Person.class.getClassLoader());
        toPerson = (Person) in.readValue(Person.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(arabicPhrase);
        dest.writeValue(englishPhrase);
        dest.writeValue(categoryFirst);
        dest.writeValue(categorySecond);
        dest.writeValue(fromPerson);
        dest.writeValue(toPerson);
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
