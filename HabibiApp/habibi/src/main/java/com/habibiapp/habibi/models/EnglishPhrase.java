package com.habibiapp.habibi.models;

/**
 * Created by samsoom on 4/9/14.
 */
public class EnglishPhrase extends Phrase {
    private ArabicPhrase arabicPhrase;
    private ProperBiziPhrase properBiziPhrase;
    private ArabiziPhrase arabiziPhrase;
    private CategoryFirst categoryFirst;
    private CategorySecond categorySecond;
    private Person fromPerson;
    private Person toPerson;
    private Dialect dialect;

    public EnglishPhrase (String arabicPhrase, String properBiziPhrase,
                            String arabiziPhrase, String phrase,
                            CategoryFirst firstCategory, CategorySecond secondCategory) {
        super(phrase);
        this.arabicPhrase = new ArabicPhrase(arabicPhrase);
        this.properBiziPhrase = new ProperBiziPhrase(properBiziPhrase);
        this.arabiziPhrase = new ArabiziPhrase(arabiziPhrase);
        this.categoryFirst = firstCategory;
        this.categorySecond = secondCategory;
    }

    public EnglishPhrase (String phrase, CategoryFirst categoryFirst) {
        super(phrase);
        this.categoryFirst = categoryFirst;
    }

    public EnglishPhrase (String phrase) {
        super(phrase);
    }

    public String getArabicPhrase(){
        return arabicPhrase.getPhrase();
    }

    public String getArabiziPhrase(){
        return arabiziPhrase.getPhrase();
    }

    public String getProperPhrase() { return properBiziPhrase.getPhrase();}

    public CategoryFirst getCategoryFirst() { return this.categoryFirst; }

    public CategorySecond getCategorySecond() { return this.categorySecond; }

    public void setArabicPhrase(ArabicPhrase arabicPhrase) {
        this.arabicPhrase = arabicPhrase;
    }

    public void setArabiziPhrase(ArabiziPhrase arabiziPhrase) { this.arabiziPhrase = arabiziPhrase; }

    public void setProperBiziPhrase(ProperBiziPhrase properBiziPhrase) { this.properBiziPhrase = properBiziPhrase; }

    public void setAdresserPerson(Person fromPerson) {
        this.fromPerson = fromPerson;
    }

    public void setAdresseePerson(Person toPerson) {
        this.toPerson = toPerson;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public boolean isEqualFirstCategory(CategoryFirst categoryFirst) { return this.categoryFirst == categoryFirst; }
    public boolean isEqualSecondCategory(CategorySecond categorySecond) { return this.categorySecond == categorySecond; }
    public boolean isEqualFromGender(Person fromGender) { return this.fromPerson == fromGender; }
    public boolean isEqualToGender(Person toGender) { return this.toPerson == toGender; }

    public enum
    {
        Flirt, Meetup, Mood, Answers
    }

    public enum CategorySecond {
        I, You, Question
    }

    public enum Person {
        All, Male, Female, MalePlural, FemalePlural
    }

    public enum Dialect {
        All, Jordanian, Saudi, Egyptian, Lebanese
    }
}