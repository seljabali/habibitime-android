package com.codingcamels.habibitime.bibi;

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

import com.codingcamels.habibitime.MainActivity;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.datasources.CategoryDataSource;
import com.codingcamels.habibitime.datasources.PhraseDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Gender;
import com.codingcamels.habibitime.models.Language;
import com.codingcamels.habibitime.models.Phrase;
import com.codingcamels.habibitime.utilities.Utils;
import com.codingcamels.habibitime.utilities.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by habibi on 8/9/14.
 */
public class BibiService extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    private ListPopupWindow currentPopUp;
    private boolean isShowingPhrases;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        clearCurrentPopUp();
        isShowingPhrases = false;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        chatHead = new ImageView(this);
        chatHead.setAnimation(ViewUtil.inFromLeftAnimation());
        resetChatHeadIcon();
        chatHead.setOnClickListener(clickOnChatHead());
        chatHead.setOnTouchListener(tapOnChatHead(getChatHeadParams()));
        windowManager.addView(chatHead, getChatHeadParams());
    }

    //CHAT HEAD
    private View.OnClickListener clickOnChatHead() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPopUp != null && !isShowingPhrases) {
                    maximizeScreen();
                } else {
                    showCategories(chatHead);
                    chatHead.setImageResource(R.drawable.maximize);
                }
            }
        };
    }

    private View.OnTouchListener tapOnChatHead(final WindowManager.LayoutParams params) {
        return new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                resetChatHeadIcon();
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
        };
    }

    //CATEGORIES
    private void showCategories(final View anchor) {
        try {
            isShowingPhrases = false;
            currentPopUp = getNewListPopupWindow(anchor);
            CategoryDataSource categoryDataSource = new CategoryDataSource(getApplicationContext());
            categoryDataSource.open();
            final ArrayList<Category> categories = new ArrayList<Category>(categoryDataSource.getCategories());
            categoryDataSource.close();
            CategoryPopUpAdapter categoryAdapter = new CategoryPopUpAdapter(getApplicationContext(), R.id.category_view_text, categories);
            currentPopUp.setAdapter(categoryAdapter);
            currentPopUp.setOnItemClickListener(clickOnCategory(anchor, categories));
            currentPopUp.setOnDismissListener(OnDismissPopUp());
            currentPopUp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private OnItemClickListener clickOnCategory(final View anchor, final ArrayList<Category> categories) {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id3) {
                try {
                    isShowingPhrases = true;
                    currentPopUp.dismiss();
                    showPhrasesForCategory(anchor, categories.get(position));
                } catch (Throwable e) {
                    Log.e("ERROR", e.toString());
                }
            }
        };
    }

    //PHRASES
    private void showPhrasesForCategory(final View anchor, final Category category) {
        chatHead.setImageResource(R.drawable.maximize);
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
        currentPopUp.setOnItemClickListener(clickOnPhrase(phrases));
        currentPopUp.setOnDismissListener(OnDismissPopUp());
        currentPopUp.show();
    }

    private OnItemClickListener clickOnPhrase(final List<Phrase> phrases) {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id3) {
                try {
                    copyPhrase(phrases.get(position));
                    currentPopUp.dismiss();
                    resetChatHeadIcon();
                } catch (Throwable e) {
                    Log.e("ERROR", e.toString());
                }
            };
        };
    }

    private void copyPhrase(final Phrase phrase) {
        Context context = getApplicationContext();
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
    }

    private android.widget.PopupWindow.OnDismissListener OnDismissPopUp() {
        return new android.widget.PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!isShowingPhrases) {
                    resetChatHeadIcon();
                }
            }
        };
    }

    private void maximizeScreen() {
        Intent intent = new Intent(BibiService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        onDestroy();
    }

    private void resetChatHeadIcon() {
        chatHead.setImageResource(R.drawable.ic_launcher);
    }

    private void clearCurrentPopUp() {
        if (currentPopUp != null) {
            currentPopUp.dismiss();
            currentPopUp = null;
        }
    }

    //SETUP
    private ListPopupWindow getNewListPopupWindow(View anchor) {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final ListPopupWindow popup = new ListPopupWindow(this);
        popup.setAnchorView(anchor);
        popup.setWidth((int) (display.getWidth()/(1.5)));
        return popup;
    }

    private WindowManager.LayoutParams getChatHeadParams() {
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
        return params;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) {
            currentPopUp = null;
            try {
                windowManager.removeView(chatHead);
            } catch(Exception e) {
                //Do nothing;
            }
        }
    }
}