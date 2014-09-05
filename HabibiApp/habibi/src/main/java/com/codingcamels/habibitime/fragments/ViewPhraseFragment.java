package com.codingcamels.habibitime.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.OnSwipeTouchListener;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.StringUtil;
import com.codingcamels.habibitime.Utils;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;

import java.util.List;

/**
* Created by habibi on 5/8/14.
*/
public class ViewPhraseFragment extends Fragment {

    public static String TAG = ViewPhraseFragment.class.getSimpleName();
    private static final String PHRASE_KEY = "phrase";
    private static final String CATEGORY_KEY = "category";
    private Phrase originalPhrase;
    private Category category;
    private SoundPool soundPool;
    private int soundID;
    private boolean loaded = false;
    private static final int MAX_STREAMS = 1;
    private static final int SOURCE_QUALITY = 0;
    private static final int PRIORITY = 1;
    private static final int LOOP = 0;
    private static final float RATE = 1f;
    private static final String PATH = "raw/";
    private TextView englishTextView;
    private TextView arabicTextView;
    private ImageView arabicTextShare;
    private ImageView arabicTextCopy;
    private TextView arabiziTextView;
    private ImageView arabiziTextShare;
    private ImageView arabiziTextCopy;
    private TextView properBiziTextView;
    private ImageView properBiziTextShare;
    private ImageView properBiziTextCopy;
    private ImageView toMaleButton;
    private ImageView toFemaleButton;
    private ImageView playSoundButton;

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
        Bundle args = getArguments();
        originalPhrase = args.getParcelable(PHRASE_KEY);
        category = args.getParcelable(CATEGORY_KEY);

        final View view = inflater.inflate(R.layout.view_habibi_phrase, container, false);
        englishTextView = (TextView) view.findViewById(R.id.english_phrase);
        arabicTextView = (TextView) view.findViewById(R.id.arabic_phrase);
        arabicTextShare = (ImageView) view.findViewById(R.id.arabic_share_button);
        arabicTextCopy = (ImageView) view.findViewById(R.id.arabic_copy_button);
        arabiziTextView = (TextView) view.findViewById(R.id.arabizi_phrase);
        arabiziTextShare = (ImageView) view.findViewById(R.id.arabizi_share_button);
        arabiziTextCopy = (ImageView) view.findViewById(R.id.arabizi_copy_button);
        properBiziTextView = (TextView) view.findViewById(R.id.properbizi_phrase);
        properBiziTextShare = (ImageView) view.findViewById(R.id.properizi_share_button);
        properBiziTextCopy = (ImageView) view.findViewById(R.id.properbizi_copy_button);
        toMaleButton = (ImageView) view.findViewById(R.id.switch_to_male);
        toFemaleButton = (ImageView) view.findViewById(R.id.switch_to_female);
        playSoundButton = (ImageView) view.findViewById(R.id.button_play_sound);

        final Gender fromGender = ((MainActivity)getActivity()).getFromGenderSettings();
        Gender toGender = ((MainActivity)getActivity()).getToGenderSettings();

        init(fromGender, toGender);

        if (category.equals(Category.MOOD) || category.equals(Category.ANSWER)) {
            init(fromGender, null);
        } else {
            toMaleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init(fromGender, Gender.MALE);
                    view.invalidate();
                }
            });
            toFemaleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init(fromGender, Gender.FEMALE);
                    view.invalidate();
                }
            });
        }

        view.setOnTouchListener(
            new OnSwipeTouchListener(getActivity()) {
                @Override
                public void onSwipeRight() {
                    getActivity().onBackPressed();
                }
        });

        return view;
    }

    private void init(Gender fromGender, Gender toGender) {
        Phrase translatedPhrase = getTranslatedPhrase(fromGender, toGender);
        setEnglishPhrase(originalPhrase);
        setArabicText(translatedPhrase);
        setArabiziText(translatedPhrase);
        setProperBiziText(translatedPhrase);
        setGenderButtons(toGender);
        setPlaySound(fromGender, toGender);

    }

    private void setGenderButtons(Gender toGender) {
        if (toGender == null) {
            toMaleButton.setVisibility(View.GONE);
            toFemaleButton.setVisibility(View.GONE);
        } else if (Gender.MALE.equals(toGender)) {
            toMaleButton.setBackground(getActivity().getResources().getDrawable(R.drawable.border));
            toFemaleButton.setBackgroundColor(android.R.color.transparent);
        } else {
            toMaleButton.setBackgroundColor(android.R.color.transparent);
            toFemaleButton.setBackground(getActivity().getResources().getDrawable(R.drawable.border));
        }
    }

    private void setPlaySound(Gender fromGender, Gender toGender) {
        int soundFile = getSoundFile(fromGender, toGender);
        loadSoundFile(soundFile);
    }
    private int getSoundFile(Gender fromGender, Gender toGender) {
        int soundFile;
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
        return soundFile;
    }

    private void loadSoundFile(int soundFile) {
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

    private Phrase getTranslatedPhrase(Gender fromGender, Gender toGender) {
        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        List<Phrase> translatedPhrases = phraseDataSource.getPhrases(originalPhrase.getHabibiPhraseId(), null,
                fromGender, toGender, Language.ARABIC, null);
        phraseDataSource.close();
        return translatedPhrases.get(0);
    }

    private void setEnglishPhrase(Phrase englishPhrase) {
        SpannableString content = new SpannableString(englishPhrase.getNativePhraseSpelling());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        englishTextView.setText(content);
    }

    private void setArabicText(Phrase arabicPhrase) {
        final String arabicText = arabicPhrase.getNativePhraseSpelling();
        if (StringUtil.isNotEmpty(arabicText)) {
            arabicTextView.setVisibility(View.VISIBLE);
            arabicTextView.setText(arabicText);
            arabicTextCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.copyToClipboard(getActivity(), arabicText);
                }
            });
            arabicTextShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.shareText(getActivity(), arabicText);
                }
            });
        } else {
            arabicTextView.setVisibility(View.GONE);
        }
    }

    private void setArabiziText(Phrase translatedPhrase) {
        final String arabiziText = translatedPhrase.getPhoneticPhraseSpelling();
        if (StringUtil.isNotEmpty(arabicTextView.getText().toString())) {
            arabiziTextView.setVisibility(View.VISIBLE);
            arabiziTextView.setText(arabiziText);
            arabiziTextCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.copyToClipboard(getActivity(), arabiziText);
                }
            });
            arabiziTextShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.shareText(getActivity(), arabiziText);
                }
            });
        } else {
            arabiziTextView.setVisibility(View.GONE);
        }
    }

    private void setProperBiziText(Phrase translatedPhrase) {
        final String properBiziText = translatedPhrase.getProperPhoneticPhraseSpelling();
        if (StringUtil.isNotEmpty(arabicTextView.getText().toString())) {
            properBiziTextView.setVisibility(View.VISIBLE);
            properBiziTextView.setText(properBiziText);
            properBiziTextCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.copyToClipboard(getActivity(), properBiziText);
                }
            });
            properBiziTextShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.shareText(getActivity(), properBiziText);
                }
            });
        } else {
            properBiziTextView.setVisibility(View.GONE);
        }
    }

}
