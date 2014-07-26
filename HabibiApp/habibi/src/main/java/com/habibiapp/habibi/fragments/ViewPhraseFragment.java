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

import com.habibiapp.habibi.AppConfig;
import com.habibiapp.habibi.BuildConfig;
import com.habibiapp.habibi.MainActivity;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.ShareDialog;
import com.habibiapp.habibi.datasources.PhraseDataSource;
import com.habibiapp.habibi.models.Category;
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
    private static final String PATH = "raw/";
    private Gender toGender;
    private Gender fromGender;

    public static ViewPhraseFragment newInstance(Activity activity, Phrase phrase, Category category) {
        ViewPhraseFragment fragment = new ViewPhraseFragment();
        PhraseDataSource phraseDataSource = new PhraseDataSource(activity);
        phraseDataSource.open();
        Gender toGender = ((MainActivity)activity).getToGenderSettings();
        Gender fromGender = ((MainActivity)activity).getFromGenderSettings();
        List<Phrase> translatedPhrases = phraseDataSource.getPhrases(phrase.getHabibiPhraseId(), null,
                fromGender, toGender, Language.ARABIC, null);
        phraseDataSource.close();
        fragment.setGenders(toGender, fromGender);
        fragment.setSettings(activity);
        fragment.setActivity(activity);
        fragment.setPhrase(translatedPhrases.get(0));
        fragment.setOriginalPhrase(phrase);
        fragment.setSoundFile(category, phrase, translatedPhrases.get(0));
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

    private void setGenders(Gender toGender, Gender fromGender) {
        this.toGender = toGender;
        this.fromGender = fromGender;
    }

    private void setSoundFile(Category category, Phrase originalPhrase, Phrase toPhrase) {
        String fileName = originalPhrase.getNativePhraseSpelling();
        fileName = fileName.replaceAll("[^a-zA-Z\\_\\ ]", "");
        fileName = fileName.replace(" ", "_").toLowerCase().trim();
        fileName = PATH + fileName;
        soundFile = activity.getResources().getIdentifier(fileName, "raw", activity.getPackageName());
        if (soundFile == 0) {
            if (Category.MOOD.equals(category)) {
                fileName += "_" + fromGender.getGenderNameShortened();
            } else {
                fileName += "_" + toGender.getGenderNameShortened();
            }
            soundFile = activity.getResources().getIdentifier(fileName, "raw", activity.getPackageName());
        }
        if (AppConfig.DEBUG) {
            Log.e("Loading Sound",  "Loaded: " + fileName);
            assert soundFile != 0;
        }
    }

    private void showShareDialog(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(ShareDialog.TEXT, text);

        ShareDialog shareDialog = new ShareDialog();
        shareDialog.setArguments(bundle);
        shareDialog.show(getFragmentManager(), ShareDialog.TAG);
    }

    private void setSettings(Activity activity) {
        fromGender = ((MainActivity)activity).getFromGenderSettings();;
        toGender = ((MainActivity)activity).getToGenderSettings();;
    }
}
