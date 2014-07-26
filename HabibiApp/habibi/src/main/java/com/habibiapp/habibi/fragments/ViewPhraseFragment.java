package com.habibiapp.habibi.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.habibiapp.habibi.AppConfig;
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
    private static final String CATEGORY_KEY = "category";
    private Phrase originalPhrase;
    private Phrase translatedPhrase;
    private Category category;
    private SoundPool soundPool;
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

    public static ViewPhraseFragment newInstance(Phrase phrase, Category category) {
        ViewPhraseFragment fragment = new ViewPhraseFragment();
        Bundle args = new Bundle();
        args.putParcelable(PHRASE_KEY, phrase);
        args.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //SETUP ARGUMENTS
        Bundle args = getArguments();
        originalPhrase = args.getParcelable(PHRASE_KEY);
        category = args.getParcelable(CATEGORY_KEY);

        //GET TRANSLATION
        toGender = ((MainActivity)getActivity()).getToGenderSettings();
        fromGender = ((MainActivity)getActivity()).getFromGenderSettings();
        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        List<Phrase> translatedPhrases = phraseDataSource.getPhrases(originalPhrase.getHabibiPhraseId(), null,
                ((MainActivity)getActivity()).getFromGenderSettings(), ((MainActivity)getActivity()).getToGenderSettings(), Language.ARABIC, null);
        phraseDataSource.close();
        translatedPhrase = translatedPhrases.get(0);

        if (AppConfig.DEBUG) {
            if (translatedPhrases.size() > 1) {
                Log.e("ViewPhraseFragment", "We got more than one translated phrase for: " + originalPhrase.getNativePhraseSpelling());
            }
        }

        //SETUP VIEW
        View view = inflater.inflate(R.layout.view_habibi_phrase, container, false);
        TextView englishTextView = (TextView) view.findViewById(R.id.english_phrase);
        TextView arabicTextView = (TextView) view.findViewById(R.id.arabic_phrase);
        TextView arabiziTextView = (TextView) view.findViewById(R.id.arabizi_phrase);
        TextView properBiziTextView = (TextView) view.findViewById(R.id.properbizi_phrase);
        Button playSoundButton = (Button) view.findViewById(R.id.button_play_sound);

        //SHOW ENGLISH PHRASE
        SpannableString content = new SpannableString(originalPhrase.getNativePhraseSpelling());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        englishTextView.setText(content);

        //SHOW ARABIC PHRASE
        arabicTextView.setText(translatedPhrase.getNativePhraseSpelling());
        arabicTextView.setVisibility(arabicTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
        arabicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView) v).getText().toString();
                showShareDialog(text);
            }
        });

        //SHOW ARABIZI PHRASE
        arabiziTextView.setText(translatedPhrase.getPhoneticPhraseSpelling());
        arabiziTextView.setVisibility(arabiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
        arabiziTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView) v).getText().toString();
                showShareDialog(text);
            }
        });

        //SHOW ARABIZI PHRASE
        properBiziTextView.setText(translatedPhrase.getProperPhoneticPhraseSpelling());
        properBiziTextView.setVisibility(properBiziTextView.getText().toString().equals("") ? View.GONE : View.VISIBLE);
        properBiziTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView) v).getText().toString();
                showShareDialog(text);
            }
        });


        //PLAY SOUND
        setSoundFile(category, originalPhrase);
        if (soundFile != 0) {
            setupSoundPool();
            soundID = soundPool.load(getActivity(), soundFile, PRIORITY);
            playSoundButton.setVisibility(View.VISIBLE);
            playSoundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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


    private void setSoundFile(Category category, Phrase originalPhrase) {
        String fileName = originalPhrase.getNativePhraseSpelling();
        fileName = fileName.replaceAll("[^a-zA-Z\\_\\ ]", "");
        fileName = fileName.replace(" ", "_").toLowerCase().trim();
        fileName = PATH + fileName;
        soundFile = getActivity().getResources().getIdentifier(fileName, "raw", getActivity().getPackageName());
        if (soundFile == 0) {
            if (Category.MOOD.equals(category)) {
                fileName += "_" + fromGender.getGenderNameShortened();
            } else {
                fileName += "_" + toGender.getGenderNameShortened();
            }
            soundFile = getActivity().getResources().getIdentifier(fileName, "raw", getActivity().getPackageName());
        }
        if (AppConfig.DEBUG) {
            if (soundFile == 0) {
                Log.e(TAG,  "Sound Not Loaded: " + fileName);
            } else {
                Log.e(TAG,  "Sound Loaded: " + fileName);
            }
        }
    }

    private void setupSoundPool() {
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOURCE_QUALITY);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
    }

    private void showShareDialog(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(ShareDialog.TEXT, text);

        ShareDialog shareDialog = new ShareDialog();
        shareDialog.setArguments(bundle);
        shareDialog.show(getFragmentManager(), ShareDialog.TAG);
    }
}
