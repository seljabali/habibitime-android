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
import android.widget.Spinner;
import android.widget.TextView;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.*;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsoom on 1/16/15.
 */
public class AddPhraseFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @InjectView(R.id.fromGenderToGenderPhraseViewGroup)
    ViewGroup fromGenderToGenderPhraseViewGroup;
    @InjectView(R.id.copyFemaleToFemale)
    CheckBox addFemaleToFemale;
    @InjectView(R.id.addNoGender)
    CheckBox addNoGender;
    @InjectView(R.id.copyMaleToMale)
    CheckBox addMaleToMale;
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
        return (file.getAbsolutePath() + "/" + fromToGender.getName() + AUDIO_EXTENTION);
    }

    private String getFilename(FromToGender fromToGender) {
        return fromToGender.getName() + AUDIO_EXTENTION;
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

    private enum FromToGender {
        MaleToMale("M->M"),
        MaleToFemale("M->F"),
        FemaleToMale("F->M"),
        FemaleToFemale("F->F"),
        None("None");

        private String name;
        private ViewGroup viewGroup;

        FromToGender(String displayName) {
            this.name = displayName;
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
    }
}
