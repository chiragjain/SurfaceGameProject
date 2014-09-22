package com.app.surfacegame.gameutils;

import android.media.SoundPool;

public class Sound {
	int soundId;
	SoundPool soundPool;

	public Sound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	public void dispose() {
		soundPool.unload(soundId);
	}

	public void pauseAll() {
		soundPool.autoPause();
	}

	public void resumeAll() {
		soundPool.autoResume();
	}

	public void release() {
		soundPool.release();
		soundPool = null;
	}

}
