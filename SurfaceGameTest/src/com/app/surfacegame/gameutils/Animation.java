package com.app.surfacegame.gameutils;

import android.graphics.Bitmap;

public class Animation {
	public static final int ANIMATION_LOOPING = 0;
	public static final int ANIMATION_NONLOOPING = 1;

	final Bitmap[] keyFrames;	
	final float frameDuration;

	public Animation(float frameDuration, Bitmap[] keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}

	public Bitmap getKeyFrame(float stateTime, int mode) {
		int frameNumber = (int) (stateTime / frameDuration);
		if (mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
		} else {
			frameNumber = frameNumber % keyFrames.length;
		}
		return keyFrames[frameNumber];
	}

	public void recycleBitmaps() {
		for (Bitmap b : keyFrames)
			b.recycle();
	}
}
