package com.codingcamels.habibitime.fragments;

import android.app.Fragment;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.datasources.HabibiPhraseDataSource;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.*;
import com.codingcamels.habibitime.utilities.StringUtil;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.internal.ListenerClass;

/**
 * Created by samsoom on 1/16/15.
 */
public class AddPhraseFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @InjectView(R.id.englishEditText)
    EditText englishEditText;
    @InjectView(R.id.fromGenderToGenderPhraseViewGroup)
    ViewGroup fromGenderToGenderPhraseViewGroup;
    @InjectView(R.id.copyFemaleToFemale)
    CheckBox addFemaleToFemale;
    @InjectView(R.id.copyMaleToMale)
    CheckBox addMaleToMale;
    @InjectView(R.id.addNoGender)
    CheckBox addNoGender;
    @InjectView(R.id.savePhrase)
    Button btnSave;
    @InjectView(R.id.categorySpinner)
    Spinner categorySpinner;

    public static final String TAG = AddPhraseFragment.class.getSimpleName();

    private static final String AUDIO_EXTENTION = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "HabibiTimeAudio";
    private static final int AUDIO_FORMAT = MediaRecorder.OutputFormat.MPEG_4;

    private MediaRecorder recorder = null;
    private LayoutInflater inflater;
    private Category selectedCategory;

    public static AddPhraseFragment newInstance() {
        return new AddPhraseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_add_phrase, container, false);
        ButterKnife.inject(this, view);
        if (view == null) {
            return null;
        }

        ArrayAdapter<CharSequence> categorySelectionAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, Category.CATEGORIES_STR);
        categorySelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySelectionAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        addPhrase(FromToGender.MaleToFemale);
        addPhrase(FromToGender.FemaleToMale);

        addMaleToMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setVisibility(FromToGender.MaleToMale, isChecked);
                setVisibility(FromToGender.None, false);
                addNoGender.setChecked(false);
                FromToGender.FemaleToMale.setTitle(getTitle(FromToGender.FemaleToMale));
            }
        });

        addFemaleToFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setVisibility(FromToGender.FemaleToFemale, isChecked);
                setVisibility(FromToGender.None, false);
                addNoGender.setChecked(false);
                FromToGender.MaleToFemale.setTitle(getTitle(FromToGender.MaleToFemale));
            }
        });
        addNoGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addFemaleToFemale.setChecked(false);
                    addMaleToMale.setChecked(false);
                    setVisibility(FromToGender.FemaleToMale, false);
                    setVisibility(FromToGender.MaleToFemale, false);
                    setVisibility(FromToGender.MaleToMale, false);
                    setVisibility(FromToGender.FemaleToFemale, false);
                    setVisibility(FromToGender.None, true);
                    addNoGender.setChecked(true);
                } else {
                    setVisibility(FromToGender.FemaleToMale, true);
                    setVisibility(FromToGender.MaleToFemale, true);
                    setVisibility(FromToGender.None, false);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = (String) parent.getItemAtPosition(pos);
        if (selected != null && !selected.isEmpty()) {
            selectedCategory = Category.getCategoryFromName(selected);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    private void setVisibility(FromToGender fromToGender, boolean visible) {
        final ViewGroup fromToGenderViewGroup = fromToGender.getViewGroup();
        if (fromToGenderViewGroup == null) {
            if (visible) {
                addPhrase(fromToGender);
            }
        } else {
            fromToGenderViewGroup.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void addPhrase(final FromToGender fromToGender) {
        ViewGroup viewAddArabicPhrase = (ViewGroup) inflater.inflate(R.layout.view_add_arabic_phrase, fromGenderToGenderPhraseViewGroup, false);
        fromToGender.setViewGroup(viewAddArabicPhrase);

        TextView title = (TextView) viewAddArabicPhrase.findViewById(R.id.fromGenderToGenderTitle);
        title.setText(getTitle(fromToGender));
        Button btnRecord = (Button) viewAddArabicPhrase.findViewById(R.id.record);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startRecording(fromToGender);
                        break;
                    case MotionEvent.ACTION_UP:
                        stopRecording();
                        break;
                }
                return false;
            }
        });
        Button btnPlay = (Button) viewAddArabicPhrase.findViewById(R.id.play_back);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.playSound(getActivity(), getFilename(fromToGender));
            }
        });
        fromGenderToGenderPhraseViewGroup.addView(viewAddArabicPhrase);
    }

    private void startRecording(FromToGender fromToGender) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(AUDIO_FORMAT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilePath(fromToGender));
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }

    private String getTitle(FromToGender fromToGender) {
        if (fromToGender.equals(FromToGender.MaleToFemale)) {
            if (addFemaleToFemale.isChecked()) {
                return FromToGender.MaleToFemale.getName();
            } else {
                return FromToGender.MaleToFemale.getName()
                        + ", "
                        + FromToGender.FemaleToFemale.getName();
            }
        }
        if (fromToGender.equals(FromToGender.FemaleToMale)) {
            if (addMaleToMale.isChecked()) {
                return FromToGender.FemaleToMale.getName();
            } else {
                return FromToGender.FemaleToMale.getName()
                        + ", "
                        + FromToGender.MaleToMale.getName();
            }
        }
        return fromToGender.getName();
    }

    private String getFilePath(FromToGender fromToGender) {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + fromToGender.getName() + AUDIO_EXTENTION;
    }

    private String getFilename(FromToGender fromToGender) {
        return fromToGender.getName() + AUDIO_EXTENTION;
    }

    private String getFilenameIfExists(FromToGender fromToGender) {
        String filePath = getFilePath(fromToGender);
        File file = new File(filePath);
        if (!file.exists()){
            return "";
        }
        return getFilename(fromToGender);
    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Log.e("Sound Error: ", mr.toString() + " what: " + what + " extra: " + extra);
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Log.v("Sound Info: ", mr.toString() + " what: " + what + ", extra: " + extra);
        }
    };

    private void save() {
        final String englishText = englishEditText.getEditableText().toString();
        if (StringUtil.isEmpty(englishText)) {
            Toast.makeText(getActivity(), "Can't save with no English text.", Toast.LENGTH_SHORT).show();
            return;
        }
        HabibiPhraseDataSource habibiPhraseDataSource = new HabibiPhraseDataSource(getActivity());
        habibiPhraseDataSource.open();
        long habibiId = habibiPhraseDataSource.createHabibiPhrase(selectedCategory);
        if (habibiId == -1) {
            Toast.makeText(getActivity(), "Error found when saving Habibi Phrase.", Toast.LENGTH_SHORT).show();
            return;
        }
        habibiPhraseDataSource.close();

        PhraseDataSource phraseDataSource = new PhraseDataSource(getActivity());
        phraseDataSource.open();
        phraseDataSource.createPhrase(habibiId, Language.ENGLISH.getId(), -1, -1, -1, englishText, null, null);

        if (addNoGender.isChecked()) {
            saveFromToGenderPhraseDb(habibiId, phraseDataSource, FromToGender.None);
            phraseDataSource.close();
            return;
        }
        if (addFemaleToFemale.isChecked()) {
            saveFromToGenderPhraseDb(habibiId, phraseDataSource, FromToGender.FemaleToFemale);
        }
        if (addMaleToMale.isChecked()) {
            saveFromToGenderPhraseDb(habibiId, phraseDataSource, FromToGender.MaleToMale);
        }
        saveFromToGenderPhraseDb(habibiId, phraseDataSource, FromToGender.FemaleToMale);
        saveFromToGenderPhraseDb(habibiId, phraseDataSource, FromToGender.MaleToFemale);
        phraseDataSource.close();
    }

    private void saveFromToGenderPhraseDb(long habibiId, PhraseDataSource phraseDataSource, FromToGender fromToGender) {
        phraseDataSource.createPhrase(habibiId, Language.ARABIC.getId(), -1, fromToGender.getFromGenderId(),
                fromToGender.getToGenderId(), fromToGender.getArabicText(), fromToGender.getPhoneticText(),
                fromToGender.getArabiziText(), getFilenameIfExists(fromToGender));
    }

    private enum FromToGender {
        MaleToMale(Gender.MALE, Gender.MALE),
        MaleToFemale(Gender.MALE, Gender.FEMALE),
        FemaleToMale(Gender.FEMALE, Gender.MALE),
        FemaleToFemale(Gender.FEMALE, Gender.FEMALE),
        None(Gender.NONE, Gender.NONE);

        private String name;
        private ViewGroup viewGroup;
        private Gender fromGender;
        private Gender toGender;

        FromToGender(Gender fromGender, Gender toGender) {
            this.fromGender = fromGender;
            this.toGender = toGender;
            if (fromGender == Gender.NONE) {
                this.name = Gender.NONE.getGenderName();
            } else {
                this.name = fromGender.getGenderNameShortened() + "->" + toGender.getGenderNameShortened();
            }
        }

        public String getName() {
            return this.name;
        }

        public void setViewGroup(ViewGroup viewGroup) {
            this.viewGroup = viewGroup;
        }

        public ViewGroup getViewGroup() {
            return viewGroup;
        }

        public void setTitle(String title) {
            if (viewGroup != null) {
                TextView titleView = (TextView) viewGroup.findViewById(R.id.fromGenderToGenderTitle);
                if (titleView != null) {
                    titleView.setText(title);
                } else {
                    Log.e("Add Phrase", "We couldn't find title textview for " + name);
                }
            }
        }

        public String getArabicText() {
            String arabicText = "";
            if (viewGroup != null) {
                TextView arabicTitleView = (TextView) viewGroup.findViewById(R.id.arabicEditText);
                if (arabicTitleView != null) {
                    arabicText = arabicTitleView.getEditableText().toString();
                }
            }
            return arabicText;
        }

        public String getPhoneticText() {
            String phoneticText = "";
            if (viewGroup != null) {
                TextView phoneticTitleView = (TextView) viewGroup.findViewById(R.id.phoneticEditText);
                if (phoneticTitleView != null) {
                    phoneticText = phoneticTitleView.getEditableText().toString();
                }
            }
            return phoneticText;
        }

        public String getArabiziText() {
            String arabiziText = "";
            if (viewGroup != null) {
                TextView arabiziTitleView = (TextView) viewGroup.findViewById(R.id.arabiziEditText);
                if (arabiziTitleView != null) {
                    arabiziText = arabiziTitleView.getEditableText().toString();
                }
            }
            return arabiziText;
        }

        public int getFromGenderId() {
            return fromGender.getId();
        }

        public int getToGenderId() {
            return toGender.getId();
        }
    }
}
