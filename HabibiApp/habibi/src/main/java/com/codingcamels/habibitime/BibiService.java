package com.codingcamels.habibitime;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.app.Service;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListPopupWindow;

import com.codingcamels.habibitime.adapters.CategoryPopUpAdapter;
import com.codingcamels.habibitime.adapters.PhrasePopUpAdapter;
import com.codingcamels.habibitime.datasources.CategoryDataSource;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;
import com.codingcamels.habibitime.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by habibi on 8/9/14.
 */
public class BibiService extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    private ListPopupWindow currentPopUp;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.ic_launcher);
        int size = (int) getApplicationContext().getResources().getDimension(R.dimen.bibi_icon_size);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                size,
                size,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;

        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow(chatHead);
            }
        });

        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                    case MotionEvent.ACTION_UP:

                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatHead, params);
                        return false;
                }
                return false;
            }
        });

        windowManager.addView(chatHead, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) {
            windowManager.removeView(chatHead);
        }
    }

    private void initiatePopupWindow(final View anchor) {
        try {
            this.currentPopUp = getNewListPopupWindow(anchor);
            CategoryDataSource categoryDataSource = new CategoryDataSource(getApplicationContext());
            categoryDataSource.open();
            final ArrayList<Category> categories = new ArrayList<Category>(categoryDataSource.getCategories());
            categoryDataSource.close();
            CategoryPopUpAdapter categoryAdapter = new CategoryPopUpAdapter(getApplicationContext(), R.id.category_view_text, categories);
            currentPopUp.setAdapter(categoryAdapter);
            currentPopUp.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id3) {
                    try {
                        currentPopUp.dismiss();
                        showPhrasesForCategory(anchor, categories.get(position));
                    } catch (Throwable e) {
                        Log.e("ERROR", e.toString());
                    }
                }
            });
            //TODO: Set the window to be to the left once on the right hand side.
//            currentPopUp.setHorizontalOffset(+100);
            currentPopUp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPhrasesForCategory(final View anchor, final Category category) {
        currentPopUp = getNewListPopupWindow(anchor);
        PhraseDataSource phraseDataSource = new PhraseDataSource(getApplicationContext());
        phraseDataSource.open();
        final List<Phrase> phrases;
        if (Category.SETTINGS.equals(category)) {
            phrases = phraseDataSource.getPhrases(-1, null, null, null, Language.ENGLISH, null);
        } else {
            phrases = phraseDataSource.getPhrases(-1, category, null, null, Language.ENGLISH, null);
        }
        phraseDataSource.close();
        PhrasePopUpAdapter phraseAdapter = new PhrasePopUpAdapter(getApplicationContext(), R.id.category_view_text, phrases, category);
        currentPopUp.setAdapter(phraseAdapter);
        currentPopUp.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id3) {
                try {
                    currentPopUp.dismiss();
                    copyPhrase(anchor, phrases.get(position));
                } catch (Throwable e) {
                    Log.e("ERROR", e.toString());
                }
            }
        });
        currentPopUp.show();
    }

    private void copyPhrase(final View anchor, final Phrase phrase) {
        Context context = getApplicationContext();
        final ListPopupWindow popup = getNewListPopupWindow(anchor);
        Gender toGender = MainActivity.getToGenderSettings(context);
        Gender fromGender = MainActivity.getFromGenderSettings(context);
        PhraseDataSource phraseDataSource = new PhraseDataSource(context);
        phraseDataSource.open();
        List<Phrase> translatedPhrases = phraseDataSource.getPhrases(phrase.getHabibiPhraseId(), null,
                fromGender, toGender, Language.ARABIC, null);
        phraseDataSource.close();
        Phrase arabicTranslatedPhrase = translatedPhrases.get(0);
        String pasteType = MainActivity.getPasteTypeSetting(context);
        if (getString(R.string.arabic).equals(pasteType)) {
            Utils.copyToClipboard(getApplicationContext(), arabicTranslatedPhrase.getNativePhraseSpelling());
        } else if (getString(R.string.arabizi).equals(pasteType)) {
            Utils.copyToClipboard(getApplicationContext(), (arabicTranslatedPhrase.getProperPhoneticPhraseSpelling()));
        }
        popup.dismiss();
    }

    private ListPopupWindow getNewListPopupWindow(View anchor) {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final ListPopupWindow popup = new ListPopupWindow(this);
        popup.setAnchorView(anchor);
        popup.setWidth((int) (display.getWidth()/(1.5)));
        return popup;
    }

}
