package com.app.surfacegame.implementation;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;

import com.app.surfacegame.gameutils.Animation;
import com.app.surfacegame.gameutils.AssetsLoader;
import com.app.surfacegame.gameutils.Music;
import com.app.surfacegame.gameutils.Sound;

public class GameAssets extends AssetsLoader {

	public GameAssets(Activity activity, boolean isLoadAssets) {
		super(activity, isLoadAssets);
	}

	public Bitmap bushes[], waves[];

	public Animation beaverOut;
	private static float TRANSLATE_OUT_DURATION = 0.1f;

	public Animation beaverIn;
	private static float TRANSLATE_IN_DURATION = 0.1f;

	public Sound clickSound;
	public Music gameMusic;

	private static GameAssets mInstance;

	public static GameAssets getInstance(Activity activity, boolean isLoadAssets) {
		if (mInstance == null)
			mInstance = new GameAssets(activity, isLoadAssets);

		return mInstance;
	}

	@Override
	public void loadAssets() throws IOException {

		Bitmap temp[] = new Bitmap[8];
		for (int i = 0; i < 8; i++)
			temp[i] = getBitmap("beaverInAnimation/" + (i + 1) + ".png");

		beaverIn = new Animation(TRANSLATE_IN_DURATION, temp);

		temp = new Bitmap[9];
		for (int i = 0; i < 9; i++)
			temp[i] = getBitmap("beaverOutAnimation/" + (i + 1) + ".png");

		beaverOut = new Animation(TRANSLATE_OUT_DURATION, temp);

		bushes = new Bitmap[13];
		waves = new Bitmap[13];
		for (int i = 0; i < 13; i++) {

			bushes[i] = getBitmap("bushes/" + (i + 1) + ".png");
			waves[i] = getBitmap("waves/" + (i + 1) + ".png");
		}
		
		clickSound = getSound("click.ogg");
		gameMusic = getMusic("music.mp3");
	}

	@Override
	public void recycleAssets() {

		beaverIn.recycleBitmaps();
		beaverOut.recycleBitmaps();

		for (Bitmap b : waves)
			b.recycle();

		for (Bitmap b : bushes)
			b.recycle();
		
		clickSound.dispose();
	}
}
