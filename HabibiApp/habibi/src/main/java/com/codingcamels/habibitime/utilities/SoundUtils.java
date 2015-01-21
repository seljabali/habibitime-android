package com.codingcamels.habibitime.utilities;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created by habibi on 1/17/15.
 */
public class SoundUtils {

    public static void playSoundFromResources(Context context, String fileName) {
        try {
            SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            int soundFile = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
            int soundID = soundPool.load(context, soundFile, 1);
            soundPool.play(soundID, 30, 30, 1, 0, 1f);
        } catch (Throwable throwable) {
            Log.e("Sound Playing Error", throwable.toString());
        }
    }

    public static void playSound(String fileName) {
        try {
            SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            int soundID = soundPool.load(fileName, 1);
            soundPool.play(soundID, 30, 30, 1, 0, 1f);
        } catch (Throwable throwable) {
            Log.e("Sound Playing Error", throwable.toString());
        }
    }
}
