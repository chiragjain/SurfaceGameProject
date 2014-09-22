package com.app.surfacegame.gameutils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, OnTouchListener {

	private static final String TAG = "GameSurfaceView";
	private SurfaceThread _thread;
	private Screen mScreen;

	private GestureDetector mGestureDetector;
	private OnInputListener mInputListener;
	private OnActivityStateListener mActivityStateListener;

	private boolean isSurfaceDestroyed = false;

	public GameSurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		mGestureDetector = new GestureDetector(context, new GestureListener());
	}

	public void setOnInputListener(OnInputListener listener) {
		this.mInputListener = listener;
	}

	public void setOnActivityStateListener(OnActivityStateListener listener) {
		this.mActivityStateListener = listener;
	}

	public void setScreen(Screen screen) {
		this.mScreen = screen;
	}

	public Screen getCurrentScreen() {
		return this.mScreen;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		isSurfaceDestroyed = false;
		if (mActivityStateListener != null)
			mActivityStateListener.onSizeChanged(width, height);
		startThread();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		destroyThread();
		isSurfaceDestroyed = true;
		if (mActivityStateListener != null)
			mActivityStateListener.onDestroy();
	}

	public void startThread() {
		_thread = new SurfaceThread(getHolder(), this);
		_thread.setRunning(true);
		_thread.start();
	}

	public void destroyThread() {
		boolean retry = true;
		_thread.setRunning(false);
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void resume() {
		if (_thread != null) {
			if (!isSurfaceDestroyed) {
				destroyThread();
				startThread();
			}
		}
		if (mActivityStateListener != null)
			mActivityStateListener.onResume();

	}

	public void pause() {
		if (_thread != null) {
			_thread.setRunning(false);
		}
		if (mActivityStateListener != null)
			mActivityStateListener.onPause();
	}

	public void drawFrame(Canvas canvas, float deltaTime) {

		if (canvas != null && mScreen != null) {
			mScreen.render(canvas, deltaTime);
			mScreen.update(deltaTime);
		} else {
			Log.e(TAG, "No Screen Added!!!");
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		mGestureDetector.onTouchEvent(event);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {

		case MotionEvent.ACTION_DOWN:
			if (mInputListener != null)
				mInputListener.onTouchDown(new Vector2(event.getX(), event
						.getY()));
			break;

		case MotionEvent.ACTION_MOVE:
			if (mInputListener != null)
				mInputListener.onTouchMove(new Vector2(event.getX(), event
						.getY()));
			break;

		case MotionEvent.ACTION_UP:
			if (mInputListener != null)
				mInputListener
						.onTouchUp(new Vector2(event.getX(), event.getY()));
			break;
		}

		return true;
	}

	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mInputListener != null)
				mInputListener.onDoubleTap(new Vector2(e.getX(), e.getY()));
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (mInputListener != null)
				mInputListener.onSingleTapConfirmed(new Vector2(e.getX(), e
						.getY()));
			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			if (mInputListener != null)
				mInputListener.onSingleTap(new Vector2(e.getX(), e.getY()));
			return super.onSingleTapUp(e);
		}

	}

	public interface OnInputListener {

		public void onSingleTap(Vector2 pointCoordinate);

		public void onSingleTapConfirmed(Vector2 pointCoordinate);

		public void onDoubleTap(Vector2 pointCoordinate);

		public void onTouchDown(Vector2 pointCoordinate);

		public void onTouchUp(Vector2 pointCoordinate);

		public void onTouchMove(Vector2 pointCoordinate);

	}

	public interface OnActivityStateListener {

		public void onPause();

		public void onResume();

		public void onSizeChanged(int width, int height);

		public void onDestroy();
	}

}
