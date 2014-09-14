package com.codingcamels.habibitime.installation.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;

/**
 * Created by habibi on 9/14/14.
 */
public class BibiEnableFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = BibiEnableFragment.class.getSimpleName();
    private Button skipButton;
    private Spinner bibiPasteSelection;
    private CheckBox enableBibi;
    private LinearLayout bibiPasteSelectionLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_install_bibi_enable, null);
        if (view == null) {
            return null;
        }
        skipButton = (Button) view.findViewById(R.id.skip_button);
        bibiPasteSelectionLayout = (LinearLayout) view.findViewById(R.id.bibi_paste_selection_layout);
        bibiPasteSelection = (Spinner) view.findViewById(R.id.bibi_paste_selection);
        enableBibi = (CheckBox) view.findViewById(R.id.enable_bibi_view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedStateInstance) {
        super.onActivityCreated(savedStateInstance);

        ArrayAdapter<CharSequence> pasteSelectionAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.bibi_paste_selection, android.R.layout.simple_spinner_item);
        pasteSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bibiPasteSelection.setAdapter(pasteSelectionAdapter);
        bibiPasteSelection.setOnItemSelectedListener(this);
        enableBibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBibiEnabled(enableBibi.isChecked());
            }
        });
        bibiPasteSelection.setSelection(1);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String selected = getString(R.string.arabizi);
        MainActivity.setPasteTypeSetting(getActivity(), selected);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = (String) parent.getItemAtPosition(pos);
        MainActivity.setPasteTypeSetting(getActivity(), selected);
    }


    private void nextPage() {
        Activity activity = getActivity();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        activity.finish();
    }

    private void setBibiEnabled(boolean enabled) {
        MainActivity.setBibiEnabled(getActivity(), enabled);
        MainActivity.setUpBibi(getActivity(), enabled);
        enableBibi.setChecked(enabled);
        bibiPasteSelectionLayout.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }
}
