package com.habibiapp.habibi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.habibiapp.habibi.fragments.ViewFirstCategoriesFragment;
import com.habibiapp.habibi.fragments.ViewListOfPhrasesFragment;
import com.habibiapp.habibi.fragments.ViewPhraseFragment;
import com.habibiapp.habibi.fragments.ViewSecondCategoriesFragment;
import com.habibiapp.habibi.models.HabibiPhrase;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setHeaderTitle(null, null, null);

        ViewFirstCategoriesFragment fragment = ViewFirstCategoriesFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentLayoutContainer, fragment, ViewFirstCategoriesFragment.TAG)
                                    .commit();
    }

    public void launchSecondaryCategoryFragment(HabibiPhrase.CategoryFirst categoryFirst) {
        ViewSecondCategoriesFragment fragment = ViewSecondCategoriesFragment.newInstance(categoryFirst);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer, fragment, ViewFirstCategoriesFragment.TAG)
                .addToBackStack(ViewFirstCategoriesFragment.TAG)
                .commit();
    }

    public void launchListOfPhrasesFragment(HabibiPhrase.CategoryFirst categoryFirst, HabibiPhrase.CategorySecond categorySecond) {
        ViewListOfPhrasesFragment fragment = ViewListOfPhrasesFragment.newInstance(categoryFirst, categorySecond);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer, fragment, ViewListOfPhrasesFragment.TAG)
                .addToBackStack(ViewListOfPhrasesFragment.TAG)
                .commit();
    }

    public void launchPhraseViewFragment(HabibiPhrase habibiPhrase) {
        ViewPhraseFragment fragment = ViewPhraseFragment.newInstance(habibiPhrase);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayoutContainer, fragment, ViewPhraseFragment.TAG)
                .addToBackStack(ViewPhraseFragment.TAG)
                .commit();
    }

    public void setHeaderTitle(HabibiPhrase.CategoryFirst categoryFirst, HabibiPhrase.CategorySecond categorySecond, String phrase){
        if (categoryFirst != null) {
            this.setTitle(">" + categoryFirst.name());
        }
        if (categorySecond != null) {
            this.setTitle(this.getTitle() + ">" + categorySecond.name());
        }
        if (phrase != null) {
            this.setTitle(phrase);
        }
    }

}
