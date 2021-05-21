package au.edu.jcu.cp3406.arithmago.sound;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import au.edu.jcu.cp3406.arithmago.R;

public class AudioManager implements SoundPool.OnLoadCompleteListener {
    private final Map<Sound, Integer> soundIds;
    private final SoundPool soundPool;
    private int loadId;
    private boolean isReady;

    public AudioManager(Context context) {
        soundIds = new HashMap<>();
        soundPool = new SoundPool(4, android.media.AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(this);

        // Load sounds
        soundPool.load(context, R.raw.correct, 0);
        soundPool.load(context, R.raw.incorrect, 0);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        this.isReady = status == 0;

        Sound sound = Sound.values()[loadId++];
        Log.i("AudioManager", "loaded sound: " + sound);
        soundIds.put(sound, sampleId);
    }

    public boolean isReady() {
        return isReady;
    }

    public void play(Sound sound) {
        Integer id = soundIds.get(sound);
        if (id != null) {
            soundPool.play(id, 1, 1, 1, 0, 1);
        }
    }

    public void resume() {
        soundPool.autoResume();
    }

    public void pause() {
        soundPool.autoPause();
    }
}