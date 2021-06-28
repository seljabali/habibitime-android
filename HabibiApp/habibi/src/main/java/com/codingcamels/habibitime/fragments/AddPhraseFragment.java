package com.codingcamels.habibitime.fragments;

import android.app.Fragment;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.PhraseBuilder;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.datasources.HabibiPhraseDataSource;
import com.codingcamels.habibitime.models.*;
import com.codingcamels.habibitime.views.AddPhrase;
import com.codingcamels.habibitime.views.AddPhraseGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsoom on 1/16/15.
 */
public class AddPhraseFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, AddPhrase.AddPhraseListener {
    @InjectView(R.id.englishEditText)
    EditText englishEditText;
    @InjectView(R.id.fromGenderToGenderPhraseViewGroup)
    ViewGroup fromGenderToGenderPhraseViewGroup;
    @InjectView(R.id.all)
    CheckBox all;
    @InjectView(R.id.to_all)
    CheckBox toAll;
    @InjectView(R.id.from_all)
    CheckBox fromAll;
    @InjectView(R.id.genderless)
    CheckBox genderless;
    @InjectView(R.id.savePhrase)
    Button btnSave;
    @InjectView(R.id.categorySpinner)
    Spinner categorySpinner;

    public static final String TAG = AddPhraseFragment.class.getSimpleName();

    private static final String AUDIO_EXTENTION = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "HabibiTimeAudio";
    private static final int AUDIO_FORMAT = MediaRecorder.OutputFormat.MPEG_4;

    private MediaRecorder recorder = null;
    private Category selectedCategory;

    private AddPhraseGroup allPhrase;
    private AddPhraseGroup fromAllPhrase;
    private AddPhraseGroup toAllPhrase;
    private AddPhraseGroup genderlessPhrase;

    public static AddPhraseFragment newInstance() {
        return new AddPhraseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_phrase, container, false);
        ButterKnife.inject(this, view);
        if (view == null) {
            return null;
        }

        ArrayAdapter<CharSequence> categorySelectionAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, Category.CATEGORIES_STR);
        categorySelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySelectionAdapter);
        categorySpinner.setOnItemSelectedListener(this);


        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                uncheckAll();
                all.setChecked(isChecked);
                if (isChecked) {
                    allPhrase = new AddPhraseGroup(getActivity(), AddPhraseFragment.this, AddPhraseGroup.Type.ALL, fromGenderToGenderPhraseViewGroup);
                    fromGenderToGenderPhraseViewGroup.addView(allPhrase);
                    fromGenderToGenderPhraseViewGroup.invalidate();
                }
            }
        });

        toAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                uncheckAll();
                toAll.setChecked(isChecked);
                if (isChecked) {
                    toAllPhrase = new AddPhraseGroup(getActivity(), AddPhraseFragment.this, AddPhraseGroup.Type.TO_ALL, fromGenderToGenderPhraseViewGroup);
                    fromGenderToGenderPhraseViewGroup.addView(toAllPhrase);
                    fromGenderToGenderPhraseViewGroup.invalidate();
                }
            }
        });

        fromAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                uncheckAll();
                fromAll.setChecked(isChecked);
                if (isChecked) {
                    fromAllPhrase = new AddPhraseGroup(getActivity(), AddPhraseFragment.this, AddPhraseGroup.Type.FROM_ALL, fromGenderToGenderPhraseViewGroup);
                    fromGenderToGenderPhraseViewGroup.addView(fromAllPhrase);
                    fromGenderToGenderPhraseViewGroup.invalidate();
                }
            }
        });

        genderless.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                uncheckAll();
                genderless.setChecked(isChecked);
                if (isChecked) {
                    genderlessPhrase = new AddPhraseGroup(getActivity(), AddPhraseFragment.this, AddPhraseGroup.Type.NO_GENDER, fromGenderToGenderPhraseViewGroup);
                    fromGenderToGenderPhraseViewGroup.addView(genderlessPhrase);
                    fromGenderToGenderPhraseViewGroup.invalidate();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Phrase> enteredPhrases = getEnteredPhrases();
                HabibiPhraseDataSource habibiPhraseDataSource = new HabibiPhraseDataSource(getActivity());
                habibiPhraseDataSource.open();
                long result = habibiPhraseDataSource.createHabibiPhrase(selectedCategory, enteredPhrases);
                habibiPhraseDataSource.close();
                if (result == -1) {
                    Toast.makeText(getActivity(), "Error in saving phrase!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private List<Phrase> getEnteredPhrases() {
        List<Phrase> enteredPhrases = new ArrayList<Phrase>();
        if (all.isChecked()) {
            enteredPhrases.addAll(allPhrase.getPhrases());
        }
        if (toAll.isChecked()) {
            enteredPhrases.addAll(toAllPhrase.getPhrases());
        }
        if (fromAll.isChecked()) {
            enteredPhrases.addAll(fromAllPhrase.getPhrases());
        }
        if (genderless.isChecked()) {
            enteredPhrases.addAll(genderlessPhrase.getPhrases());
        }
        enteredPhrases.add(PhraseBuilder.createPhrase().setLanguage(Language.ENGLISH)
                            .setNativeSpelling(englishEditText.getEditableText().toString())
                            .build());
        return enteredPhrases;
    }

    private void uncheckAll() {
        all.setChecked(false);
        toAll.setChecked(false);
        fromAll.setChecked(false);
        genderless.setChecked(false);
        fromGenderToGenderPhraseViewGroup.removeAllViews();
    }

    @Override
    public void onStartRecording(String soundName) {
        if (soundName == null || "".equals(soundName)) {
            Log.e(TAG, "Tried saving empty filename");
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(AUDIO_FORMAT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getSoundFilePath(soundName));
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

    @Override
    public void onStopRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;
            } catch (Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        }
    }

    @Override
    public void onPlaySound(String soundName) {
        MainActivity.playSound(getActivity(), getSoundFilePath(soundName));
    }

    private String getSoundDirectory() {
//        String filepath = Environment.getExternalStorageDirectory().getPath();
        File filepath = getActivity().getFilesDir();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        return file.getAbsolutePath();
    }

    private String getSoundFilePath(String name) {
        File file = new File(getSoundDirectory());
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + name + AUDIO_EXTENTION;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        compoundButton.setChecked(b);
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

    @Override
    public void onClick(View view) {
        // do nothing i think
    }


}
