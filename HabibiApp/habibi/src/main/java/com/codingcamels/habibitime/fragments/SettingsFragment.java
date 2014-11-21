package com.codingcamels.habibitime.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Gender;

/**
 * Created by samsoom on 9/4/14.
 */
public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int EASTER_TAP_COUNT = 5;
    private ImageView selfGenderMale;
    private ImageView selfGenderFemale;
    private ImageView habibiGenderMale;
    private ImageView habibiGenderFemale;
    private Spinner bibiPasteSelection;
    private Drawable selectedBorder;
    private int transparent;
    private int femaleTapCount;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
        femaleTapCount = 0;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        if (view == null) {
            return null;
        }
        selfGenderMale = (ImageView) view.findViewById(R.id.gender_select_self_m_image_view);
        selfGenderFemale = (ImageView) view.findViewById(R.id.gender_select_self_f_image_view);
        habibiGenderMale = (ImageView) view.findViewById(R.id.gender_select_habibi_m_image_view);
        habibiGenderFemale = (ImageView) view.findViewById(R.id.gender_select_habibi_f_image_view);
        bibiPasteSelection = (Spinner) view.findViewById(R.id.bibi_paste_selection);
        transparent = android.R.color.transparent;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        selectedBorder = getResources().getDrawable(R.drawable.border);

        //FROM GENDER
        setFromGender(MainActivity.getFromGenderSettings(getActivity()));
        selfGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFromGender(Gender.MALE);
            }
        });
        selfGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFemaleButtonPressed();
                setFromGender(Gender.FEMALE);
            }
        });

        //TO GENDER
        setToGender(MainActivity.getToGenderSettings(getActivity()));
        habibiGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToGender(Gender.MALE);
            }
        });
        habibiGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFemaleButtonPressed();
                setToGender(Gender.FEMALE);
            }
        });

        //BIBI PASTE SELECTION
        ArrayAdapter<CharSequence> pasteSelectionAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.bibi_paste_selection, android.R.layout.simple_spinner_item);
        pasteSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bibiPasteSelection.setAdapter(pasteSelectionAdapter);
        bibiPasteSelection.setOnItemSelectedListener(this);
        if (MainActivity.getPasteTypeSetting(getActivity()).equals("Arabic")) {
            bibiPasteSelection.setSelection(0);
        } else {
            bibiPasteSelection.setSelection(1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String selected = (String) parent.getItemAtPosition(pos);
        MainActivity.setPasteTypeSetting(getActivity(), selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String selected = getString(R.string.arabizi);
        MainActivity.setPasteTypeSetting(getActivity(), selected);
    }

    private void setFromGender(Gender gender) {
        MainActivity.setFromGender(getActivity(), gender);
        if (Gender.MALE.equals(gender)) {
            selfGenderMale.setBackground(selectedBorder);
            selfGenderFemale.setBackgroundColor(transparent);
        } else {
            selfGenderFemale.setBackground(selectedBorder);
            selfGenderMale.setBackgroundColor(transparent);
        }
    }

    private void setToGender(Gender gender) {
        MainActivity.setToGender(getActivity(), gender);
        if (Gender.MALE.equals(gender)) {
            habibiGenderMale.setBackground(selectedBorder);
            habibiGenderFemale.setBackgroundColor(transparent);
        } else {
            habibiGenderFemale.setBackground(selectedBorder);
            habibiGenderMale.setBackgroundColor(transparent);
        }
    }

    private void onFemaleButtonPressed() {
        if (++femaleTapCount >= EASTER_TAP_COUNT) {
            try {
                femaleTapCount = 0;
                SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                String fileName = "raw/" + "laugh_f";
                int soundFile = getActivity().getResources().getIdentifier(fileName, "raw", getActivity().getPackageName());
                int soundID = soundPool.load(getActivity(), soundFile, 1);
                soundPool.play(soundID, 30, 30, 1, 0, 1f);
            } catch (Throwable throwable) {
                // Do nothing
            }
        }
    }
}