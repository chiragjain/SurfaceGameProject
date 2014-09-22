package com.app.surfacegame.implementation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Elastic;

import com.app.surfacegame.gameutils.Animation;
import com.app.surfacegame.gameutils.AssetsLoader.OnAssetsLoadListener;
import com.app.surfacegame.gameutils.Camera;
import com.app.surfacegame.gameutils.GameObjectAccessor;
import com.app.surfacegame.gameutils.GameSurfaceView;
import com.app.surfacegame.gameutils.OverlapTester;
import com.app.surfacegame.gameutils.Screen;
import com.app.surfacegame.gameutils.Vector2;
import com.app.surfacegame.objects.Arrow;
import com.app.surfacegame.objects.Beaver;
import com.app.surfacegame.objects.Bushes;

public class GameScreen extends Screen {

	GameAssets assets;
	Paint paint;
	Beaver beaver[];
	Arrow arrow;
	Camera camera;
	// Bushes bushes[];

	Bushes badiBush;

	boolean isAssetsLoaded;
	float stateTime;

	TweenManager manager;

	public GameScreen(Activity activity, GameSurfaceView surface) {
		super(activity, surface);

		isAssetsLoaded = false;
		assets = GameAssets.getInstance(activity, false);
		assets.setOnAssetsLoadListener(new OnAssetsLoadListener() {

			@Override
			public void OnAssetsLoaded() {
				isAssetsLoaded = true;
				assets.gameMusic.play();
			}

			@Override
			public void OnAssetsLoadError() {
				isAssetsLoaded = false;
			}
		});
		assets.loadAssetsAsync();

		manager = new TweenManager();

	}

	@Override
	public void update(float deltaTime) {
		for (Beaver b : beaver)
			b.update(deltaTime);
		
		arrow.update(deltaTime);

	}

	@Override
	public void render(Canvas canvas, float deltaTime) {
		canvas.drawColor(Color.BLACK, Mode.CLEAR);
		if (isAssetsLoaded) {
			for (Beaver b : beaver) {
				Bitmap beaverBitmap = assets.beaverIn.getKeyFrame(
						beaver[0].stateTime, Animation.ANIMATION_LOOPING);
				drawBitmap(canvas, b, beaverBitmap, camera);
			}
			drawBitmap(canvas, badiBush, assets.bushes[3], camera);
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Style.FILL);
			canvas.drawRect(camera.getScaledRect(arrow.bounds.getRect()), paint);
			manager.update(deltaTime);		
		}
	}

	@Override
	public void onSingleTap(Vector2 pointCoordinate) {
		if (OverlapTester.pointInRectangle(beaver[3].bounds, camera.touchToWorld(pointCoordinate))) {
			assets.clickSound.play(1);
			Tween.to(badiBush, GameObjectAccessor.ROTATE, 3f).ease(Elastic.OUT).target(270).start(manager);
		}
		super.onSingleTap(pointCoordinate); 
	}

	@Override
	public void onResume() {
		if (assets.gameMusic != null)
			assets.gameMusic.play();
		super.onResume();
	}

	@Override
	public void onPause() {
		if (assets.gameMusic != null)
			assets.gameMusic.stop();
		super.onPause();
	}

	@Override
	public void onSizeChanged(int width, int height) {

		beaver = new Beaver[5];
		int x = 50;
		for (int i = 0; i < 5; i++) {
			beaver[i] = new Beaver(x, 100);
			x += 100;
		}

		badiBush = new Bushes(width / 2, height / 2);
		
		if (width > height)
			camera = new Camera(1280, 768, width, height);
		else
			camera = new Camera(768, 1280, width, height);
		
		arrow = new Arrow(100, 100, 50, 50);

		super.onSizeChanged(width, height);
	}

}
