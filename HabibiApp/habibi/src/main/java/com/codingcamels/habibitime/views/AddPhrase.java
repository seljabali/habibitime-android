package com.codingcamels.habibitime.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codingcamels.habibitime.PhraseBuilder;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;
import java.util.Arrays;
import java.util.List;

/**
 * Created by samsoom on 4/1/15.
 */
public class AddPhrase extends View {

    private static final String TAG = AddPhrase.class.getSimpleName();

    private AddPhraseListener addPhraseListener;

    private Type type;
    private TextView titleView;
    private TextView phoneticTitleView;
    private TextView arabiziTitleView;
    private TextView arabicTitleView;

    public AddPhrase(Context context) {
        super(context);
    }


    public AddPhrase(Context context, AddPhraseListener addPhraseListener, Type type, ViewGroup viewGroup) {
        super(context);
        this.addPhraseListener = addPhraseListener;
        this.type = type;
        init(type, viewGroup);
    }

    private void init(Type type, ViewGroup viewGroup) {
        View view = LayoutInflater.from(getContext()).cloneInContext(getContext()).inflate(R.layout.view_add_arabic_phrase, viewGroup, false);
        titleView = (TextView) view.findViewById(R.id.fromGenderToGenderTitle);
        arabicTitleView = (TextView) view.findViewById(R.id.arabicTextView);
        phoneticTitleView = (TextView) view.findViewById(R.id.phoneticEditText);
        arabiziTitleView = (TextView) view.findViewById(R.id.arabiziEditText);
        Button playBack = (Button) view.findViewById(R.id.play_back);
        Button record = (Button) view.findViewById(R.id.record);

        switch (type) {
            case MtoF:
                titleView.setText("M->F");
                break;
            case FtoM:
                titleView.setText("F->M");
                break;
            case MtoM:
                titleView.setText("M->M");
                break;
            case FtoF:
                titleView.setText("F->F");
                break;
            case MtoF_FtoF:
                titleView.setText("M->F, F->F");
                break;
            case FtoM_MtoM:
                titleView.setText("F->M, M->M");
                break;
            case FtoM_FtoF:
                titleView.setText("F->M, F->F");
                break;
            case MtoF_MtoM:
                titleView.setText("M->F, M->M");
                break;
            case Genderless:
                titleView.setText("Genderless");
                break;
            default:
                Log.e(TAG, "Invalid AddPhrase Type!");
        }

        playBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhraseListener.onPlaySound(getPhrases().get(0).getSoundFileName());
            }
        });

        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startRecording();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopRecording();
                        break;
                }
                return false;
            }
        });

        viewGroup.addView(view);
    }

    private void startRecording() {
        String enteredText = arabiziTitleView.getText().toString();
        if (enteredText == null || "".equals(enteredText)) {
            Toast.makeText(getContext(), "Please enter text before recording!", Toast.LENGTH_SHORT).show();
            return;
        }
        addPhraseListener.onStartRecording(getPhrases().get(0).getSoundFileName());
    }

    private void stopRecording() {
        addPhraseListener.onStopRecording();
    }

    public List<Phrase> getPhrases() {
        switch (type) {
            case MtoF:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.MALE)
                                .setToGender(Gender.FEMALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case FtoM:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.FEMALE)
                                .setToGender(Gender.MALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case MtoM:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.MALE)
                                .setToGender(Gender.MALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case FtoF:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.FEMALE)
                                .setToGender(Gender.FEMALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case MtoF_FtoF:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.MALE)
                                .setToGender(Gender.FEMALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build(),
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.FEMALE)
                                .setToGender(Gender.FEMALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case FtoM_MtoM:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.FEMALE)
                                .setToGender(Gender.MALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build(),
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.MALE)
                                .setToGender(Gender.MALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case FtoM_FtoF:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.FEMALE)
                                .setToGender(Gender.MALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build(),
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.FEMALE)
                                .setToGender(Gender.FEMALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case MtoF_MtoM:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.MALE)
                                .setToGender(Gender.FEMALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build(),
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.MALE)
                                .setToGender(Gender.MALE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            case Genderless:
                return Arrays.asList(
                        PhraseBuilder.createPhrase()
                                .setFromGender(Gender.NONE)
                                .setToGender(Gender.NONE)
                                .setLanguage(Language.ARABIC)
                                .setNativeSpelling(arabicTitleView.getText().toString())
                                .setPhoneticSpelling(phoneticTitleView.getText().toString())
                                .setProperSpelling(arabiziTitleView.getText().toString())
                                .build()
                );
            default:
                Log.e(TAG, "Invalid AddPhrase Type!");
                return null;
        }
    }

    public enum Type {
        MtoF, FtoM, MtoM, FtoF, MtoF_FtoF, FtoM_MtoM, MtoF_MtoM, FtoM_FtoF, Genderless;
    }

    public interface AddPhraseListener {
        void onStartRecording(String soundName);
        void onStopRecording();
        void onPlaySound(String soundName);
    }
}
