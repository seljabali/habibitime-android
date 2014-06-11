package com.habibiapp.habibi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.habibiapp.habibi.models.HabibiPhrase;

import java.util.List;

public class MainActivity extends Activity {

    private HabibiPhraseDataSource habibiPhraseDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.container);

        habibiPhraseDataSource = new HabibiPhraseDataSource(this);
        habibiPhraseDataSource.open();

        habibiPhraseDataSource.createHabibiPhrase(1);
        habibiPhraseDataSource.createHabibiPhrase(2);
        habibiPhraseDataSource.createHabibiPhrase(3);

        List<HabibiPhrase> habibiPhrases = habibiPhraseDataSource.getAllHabibiPhrases();
        if (habibiPhrases.size() > 0) {
            HabibiPhrase habibiPhrase = habibiPhraseDataSource.getAllHabibiPhrases().get(0);
            textView.setText(textView.getText() + "\n" + "this is it: " + habibiPhrase.getPopularity());
        } else {
            textView.setText("nadaa amigoooo!");
        }
    }

}

//    public void launchSecondaryCategoryFragment(HabibiPhrase.CategoryFirst categoryFirst) {
//        ViewSecondCategoriesFragment fragment = ViewSecondCategoriesFragment.newInstance(categoryFirst);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentLayoutContainer, fragment, ViewFirstCategoriesFragment.TAG)
//                .addToBackStack(ViewFirstCategoriesFragment.TAG)
//                .commit();
//    }