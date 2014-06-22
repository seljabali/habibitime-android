//package com.habibiapp.habibi;
//
//import com.habibiapp.habibi.models.*;
//
///**
// * Created by samsoom on 4/13/14.
// */
//public class PhraseBuilder {
//    private HabibiPhrase habibiPhrase;
//
//    public PhraseBuilder() {
//        habibiPhrase = new HabibiPhrase();
//    }
//
//    public PhraseBuilder addEnglishPhrase(String phrase) {
//        habibiPhrase.setEnglishPhrase(new EnglishPhrase(phrase));
//        return this;
//    }
//
//    public PhraseBuilder addArabicPhrase(String phrase) {
//        habibiPhrase.setArabicPhrase(new ArabicPhrase(phrase));
//        return this;
//    }
//
//    public PhraseBuilder addArabiziPhrase(String phrase) {
//        habibiPhrase.getArabicPhraseObject().setPhraseArabizi(phrase);
//        return this;
//    }
//
//    public PhraseBuilder addProperBiziPhrase(String phrase) {
//        habibiPhrase.getArabicPhraseObject().setPhraseProperbizi(phrase);
//        return this;
//    }
//
//    public PhraseBuilder addFromPerson(HabibiPhrase.Person person) {
//        habibiPhrase.setFromPerson(person);
//        return this;
//    }
//
//    public PhraseBuilder addToPerson(HabibiPhrase.Person person) {
//        habibiPhrase.setToPerson(person);
//        return this;
//    }
//
//    public PhraseBuilder addFirstCategory(HabibiPhrase.CategoryFirst categoryFirst) {
//        habibiPhrase.setCategoryFirst(categoryFirst);
//        return this;
//    }
//
//    public PhraseBuilder addSecondCategory(HabibiPhrase.CategorySecond categorySecond) {
//        habibiPhrase.setCategorySecond(categorySecond);
//        return this;
//    }
//
//    public HabibiPhrase build() {
//        return habibiPhrase;
//    }
//}
