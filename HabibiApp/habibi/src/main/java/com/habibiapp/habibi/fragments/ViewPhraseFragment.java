package com.habibiapp.habibi.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.habibiapp.habibi.R;
import com.habibiapp.habibi.ShareDialog;
import com.habibiapp.habibi.datasources.PhraseDataSource;
import com.habibiapp.habibi.models.Gender;
import com.habibiapp.habibi.models.Language;
import com.habibiapp.habibi.models.Phrase;
import java.util.List;

/**
* Created by habibi on 5/8/14.
*/
public class ViewPhraseFragment extends Fragment {

        public static String TAG = ViewPhraseFragment.class.getSimpleName();
        private static final String PHRASE_KEY = "phrase";
        private Phrase phrase;
        private Phrase originalPhrase;
        private SoundPool soundPool;
        private Activity activity;
        private int soundID;
        private int soundFile = 0;
        private boolean loaded = false;
        private static final int MAX_STREAMS = 1;
        private static final int SOURCE_QUALITY = 0;
        private static final int PRIORITY = 1;
        private static final int LOOP = 0;
        private static final float RATE = 1f;


        public static ViewPhraseFragment newInstance(Activity activity, Phrase phrase) {
            ViewPhraseFragment fragment = new ViewPhraseFragment();
            PhraseDataSource phraseDataSource = new PhraseDataSource(activity);
            phraseDataSource.open();
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
            String toGender = sharedPref.getString("to_gender", "1");
            List<Phrase> translatedPhrases = phraseDataSource.getPhrases(phrase.getHabibiPhraseId(),
                    null, null, Gender.getGenderFromID(toGender), Language.ARABIC, null);
            phraseDataSource.close();
            if (translatedPhrases == null || translatedPhrases.size() != 1) {
                Log.e(TAG, "We got 1 or less translations: " + translatedPhrases.size());
            }
            fragment.setActivity(activity);
            fragment.setPhrase(translatedPhrases.get(0));
            fragment.setOriginalPhrase(phrase);
            fragment.setSoundFile(phrase);
            return fragment;
        }

        @Override
        public void onActivityCreated (Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            if (soundFile != 0) {
                soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOURCE_QUALITY);
                soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        loaded = true;
                    }
                });
                soundID = soundPool.load(activity, soundFile, PRIORITY);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.view_habibi_phrase, container, false);
            TextView englishTextView = (TextView) view.findViewById(R.id.english_phrase);
            TextView arabicTextView = (TextView) view.findViewById(R.id.arabic_phrase);
            TextView arabiziTextView = (TextView) view.findViewById(R.id.arabizi_phrase);
            TextView properBiziTextView = (TextView) view.findViewById(R.id.properbizi_phrase);
            Button playSoundButton = (Button) view.findViewById(R.id.button_play_sound);

            //ENGLISH PHRASE
            SpannableString content = new SpannableString(originalPhrase.getNativePhraseSpelling());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            englishTextView.setText(content);

            //ARABIC PHRASE
            arabicTextView.setText(phrase.getNativePhraseSpelling());
            arabicTextView.setVisibility(arabicTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
            arabicTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = ((TextView) v).getText().toString();
                    showShareDialog(text);
                }
            });

            //ARABIZI PHRASE
            arabiziTextView.setText(phrase.getPhoneticPhraseSpelling());
            arabiziTextView.setVisibility(arabiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
            arabiziTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = ((TextView) v).getText().toString();
                    showShareDialog(text);
                }
            });

            //ARABIZI PHRASE
            properBiziTextView.setText(phrase.getProperPhoneticPhraseSpelling());
            properBiziTextView.setVisibility(properBiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
            properBiziTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = ((TextView) v).getText().toString();
                    showShareDialog(text);
                }
            });


            if (soundFile != 0) {
                playSoundButton.setVisibility(View.VISIBLE);
                playSoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        float volume = actualVolume / maxVolume;
                        if (loaded) {
                            soundPool.play(soundID, volume, volume, PRIORITY, LOOP, RATE);
                        }
                    }
                });
            }
            return view;
        }

    private void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }
    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void setOriginalPhrase(Phrase phrase) {
        this.originalPhrase = phrase;
    }
    private void setSoundFile(Phrase originalPhrase) {
        String fileName = originalPhrase.getNativePhraseSpelling();
        fileName = fileName.replace(" ", "_");
        fileName = fileName.replace("?", "");
        fileName = fileName.replace(".", "");
        fileName = fileName.toLowerCase();
        soundFile = activity.getResources().getIdentifier("raw/"+fileName, "raw", activity.getPackageName());
    }

    private void showShareDialog(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(ShareDialog.TEXT, text);

        ShareDialog shareDialog = new ShareDialog();
        shareDialog.setArguments(bundle);
        shareDialog.show(getFragmentManager(), ShareDialog.TAG);
    }
}
