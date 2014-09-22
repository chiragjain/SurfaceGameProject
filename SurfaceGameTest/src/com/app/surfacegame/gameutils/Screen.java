package com.app.surfacegame.gameutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import aurelienribon.tweenengine.Tween;

import com.app.surfacegame.gameutils.GameSurfaceView.OnActivityStateListener;
import com.app.surfacegame.gameutils.GameSurfaceView.OnInputListener;

public abstract class Screen implements OnInputListener,
		OnActivityStateListener {

	private GameSurfaceView surface;
	private Activity mActivity;

	public Screen(Activity activity, GameSurfaceView surface) {
		this.mActivity = activity;
		this.surface = surface;
		this.surface.setOnInputListener(this);
		this.surface.setOnActivityStateListener(this);
		Tween.registerAccessor(GameObject.class, new GameObjectAccessor());
	}

	public GameSurfaceView getGameSurfaceView() {
		return this.surface;
	}

	public Activity getActivity() {
		return this.mActivity;
	}

	public abstract void update(float deltaTime);

	public abstract void render(Canvas canvas, float deltaTime);

	@Override
	public void onSingleTap(Vector2 pointCoordinate) {
	}

	@Override
	public void onSingleTapConfirmed(Vector2 pointCoordinate) {
	}

	@Override
	public void onDoubleTap(Vector2 pointCoordinate) {
	}

	@Override
	public void onTouchDown(Vector2 pointCoordinate) {
	}

	@Override
	public void onTouchUp(Vector2 pointCoordinate) {
	}

	@Override
	public void onTouchMove(Vector2 pointCoordinate) {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onSizeChanged(int width, int height) {
	}

	@Override
	public void onDestroy() {
	}

	protected void drawBitmap(Canvas canvas, GameObject object, Bitmap bitmap,
			Camera camera) {
		Paint paint = new Paint();
		paint.setAlpha((int) (object.alpha * 255));

		if (object.angle != 0) {
			canvas.save();
			if (camera != null)
				canvas.rotate(object.angle,
						object.anchor.x * camera.getScalingFactorX(),
						object.anchor.y * camera.getScalingFactorY());
			else
				canvas.rotate(object.angle, object.anchor.x, object.anchor.y);
		}

		canvas.drawBitmap(bitmap, null,
				camera != null ? camera.getScaledRect(object.bounds.getRect())
						: object.bounds.getRect(), paint);
		
		if (object.angle != 0) {
			canvas.restore();
		}
	}

}
