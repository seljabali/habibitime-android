package com.codingcamels.habibitime.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.utilities.AndroidUtils;
import com.codingcamels.habibitime.utilities.ShareDialog;
import com.codingcamels.habibitime.utilities.SoundUtils;
import com.codingcamels.habibitime.utilities.StringUtils;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;

import java.io.File;
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
    private TextView toGenderLabel;
    private ImageView playSoundButton;
    private boolean soundOnSd;

    public static ViewPhraseFragment newInstance(Phrase phrase, Category category) {
        ViewPhraseFragment fragment = new ViewPhraseFragment();
        Bundle args = new Bundle();
        args.putParcelable(PHRASE_KEY, phrase);
        args.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_phrase, container, false);
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
        toGenderLabel = (TextView) view.findViewById(R.id.to_gender_label);
        playSoundButton = (ImageView) view.findViewById(R.id.button_play_sound);

        Bundle args = getArguments();
        originalPhrase = args.getParcelable(PHRASE_KEY);
        category = args.getParcelable(CATEGORY_KEY);
        final Gender fromGender = MainActivity.getFromGenderSettings(getActivity());
        final Gender toGender = MainActivity.getToGenderSettings(getActivity());

        if (category.equals(Category.MOOD) || category.equals(Category.ANSWER)) {
            init(fromGender, null);
        } else {
            init(fromGender, toGender);
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
        return view;
    }

    private void init(Gender fromGender, Gender toGender) {
        Phrase translatedPhrase = getTranslatedPhrase(fromGender, toGender);
        if (translatedPhrase == null) {
            Toast.makeText(getActivity(), "Can't find translation!", Toast.LENGTH_SHORT).show();
            return;
        }
        setEnglishPhrase(originalPhrase);
        setArabicText(translatedPhrase);
        setArabiziText(translatedPhrase);
        setProperBiziText(translatedPhrase);
        setTextOnClick(arabicTextView);
        setTextOnClick(arabiziTextView);
        setTextOnClick(properBiziTextView);
        setGenderButtons(toGender);
        loadPlaySound(translatedPhrase);
    }

    private void setGenderButtons(Gender toGender) {
        if (toGender == null || toGender == Gender.NONE) {
            toGenderLabel.setVisibility(View.GONE);
            toMaleButton.setVisibility(View.GONE);
            toFemaleButton.setVisibility(View.GONE);
        } else if (Gender.MALE.equals(toGender)) {
            toMaleButton.setBackground(getResources().getDrawable(R.drawable.border));
            toFemaleButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            toMaleButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            toFemaleButton.setBackground(getResources().getDrawable(R.drawable.border));
        }
    }

    private int getSoundId(Phrase phrase) {
        String fileName = phrase.getSoundFileName();
        int sound = SoundUtils.loadSoundFromResource(getActivity(), fileName);
        if (sound == 0) {
            File externalFile = new File(MainActivity.getSoundDirectory(), fileName);
            sound = SoundUtils.loadSound(externalFile.getAbsolutePath());
            soundOnSd = true;
        } else {
            soundOnSd = false;
        }
        if (sound == 0) {
            Log.e(TAG, "Can't find sound: " + fileName);
        }
        return sound;
    }

    private void loadPlaySound(final Phrase phrase) {
        final int soundId = getSoundId(phrase);
        if (soundId != 0) {
            playSoundButton.setVisibility(View.VISIBLE);
            playSoundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fileName = phrase.getSoundFileName();
                    if (soundOnSd) {
                        File file = new File(MainActivity.getSoundDirectory(), fileName);
                        SoundUtils.playSound(file.getAbsolutePath());
                    } else {
                        SoundUtils.playSoundFromResources(getActivity(), fileName);
                    }
                }
            });
        }
    }

    private Phrase getTranslatedPhrase(Gender fromGender, Gender toGender) {
        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        List<Phrase> translatedPhrases = phraseDataSource.getPhrases(originalPhrase.getHabibiPhraseId(), null,
                fromGender, toGender, Language.ARABIC, null);
        phraseDataSource.close();
        return translatedPhrases == null || translatedPhrases.isEmpty() ? null : translatedPhrases.get(0);
    }

    private void setEnglishPhrase(Phrase englishPhrase) {
        SpannableString content = new SpannableString(englishPhrase.getNativePhraseSpelling());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        englishTextView.setText(content);
    }

    private void setArabicText(Phrase arabicPhrase) {
        final String arabicText = arabicPhrase.getNativePhraseSpelling();
        if (StringUtils.isNotEmpty(arabicText)) {
            arabicTextView.setVisibility(View.VISIBLE);
            arabicTextView.setText(arabicText);
            arabicTextCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidUtils.copyToClipboard(getActivity(), arabicText);
                }
            });
            arabicTextShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidUtils.shareText(getActivity(), arabicText);
                }
            });
        } else {
            arabicTextView.setVisibility(View.GONE);
        }
    }

    private void setArabiziText(Phrase translatedPhrase) {
        final String arabiziText = translatedPhrase.getPhoneticPhraseSpelling();
        if (StringUtils.isNotEmpty(arabicTextView.getText().toString())) {
            arabiziTextView.setVisibility(View.VISIBLE);
            arabiziTextView.setText(arabiziText);
            arabiziTextCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidUtils.copyToClipboard(getActivity(), arabiziText);
                }
            });
            arabiziTextShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidUtils.shareText(getActivity(), arabiziText);
                }
            });
        } else {
            arabiziTextView.setVisibility(View.GONE);
        }
    }

    private void setProperBiziText(Phrase translatedPhrase) {
        final String properBiziText = translatedPhrase.getProperPhoneticPhraseSpelling();
        if (StringUtils.isNotEmpty(arabicTextView.getText().toString())) {
            properBiziTextView.setVisibility(View.VISIBLE);
            properBiziTextView.setText(properBiziText);
            properBiziTextCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidUtils.copyToClipboard(getActivity(), properBiziText);
                }
            });
            properBiziTextShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AndroidUtils.shareText(getActivity(), properBiziText);
                }
            });
        } else {
            properBiziTextView.setVisibility(View.GONE);
        }
    }

    private void setTextOnClick(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog.show(getActivity(), textView.getText().toString());
            }
        });
    }
}