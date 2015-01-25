package com.codingcamels.habibitime.utilities;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created by habibi on 1/17/15.
 */
public class SoundUtils {
    private static final String TAG = SoundUtils.class.getSimpleName();

    private static final String AUDIO_EXTENTION = ".mp4";
    private static final int MAX_STREAMS = 1;
    private static final int SOURCE_QUALITY = 0;
    private static final int PRIORITY = 1;
    private static final int LOOP = 0;
    private static final float RATE = 1f;
    private static final float VOLUME = 30f;

    public static void playSoundFromResources(Context context, String fileName) {
        try {
            SoundPool soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
            int soundFile = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
            int soundId = soundPool.load(context, soundFile, PRIORITY);
            soundPool.play(soundId, VOLUME, VOLUME, PRIORITY, LOOP, RATE);
        } catch (Throwable throwable) {
            Log.e(TAG, "Error playing sound from resource: " + fileName + ", " + throwable.toString());
        }
    }

    public static void playSound(String fileName) {
        try {
            SoundPool soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
            int soundId = soundPool.load(fileName, PRIORITY);
            soundPool.play(soundId, VOLUME, VOLUME, PRIORITY, LOOP, RATE);
        } catch (Throwable throwable) {
            Log.e(TAG, "Error playing sound: " + fileName + ", " + throwable.toString());
        }
    }

    public static void playSound(int soundId) {
        try {
            SoundPool soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOURCE_QUALITY);
            soundPool.play(soundId, VOLUME, VOLUME, PRIORITY, LOOP, RATE);
        } catch (Throwable throwable) {
            Log.e(TAG, "Error playing soundId: " + soundId + ", " +  throwable.toString());
        }
    }

    public static int loadSoundFromResource(Context context, String fileName) {
        int soundID = 0;
        try {
            SoundPool soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOURCE_QUALITY);
            int soundFile = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
            soundID = soundPool.load(context, soundFile, PRIORITY);
        } catch (Throwable throwable) {
            Log.e(TAG, "Error loading sound from resource: " + fileName + ", " +  throwable.toString());
        }
        return soundID;
    }

    public static int loadSound(String fileName) {
        int soundID = 0;
        try {
            SoundPool soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOURCE_QUALITY);
            soundID = soundPool.load(fileName, 1);
        } catch (Throwable throwable) {
            Log.e(TAG, "Error Loading sound: " + fileName + ", " +  throwable.toString());
        }
        return soundID;
    }

    public static float getVolume(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return actualVolume / maxVolume;
    }
}
