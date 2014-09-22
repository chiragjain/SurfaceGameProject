package com.app.surfacegame.gameutils;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class SurfaceThread extends Thread {

	private SurfaceHolder _surfaceHolder;
	private GameSurfaceView _panel;
	private boolean _run = false;

	// If we are able to get as high as this FPS, don't render again.
	final float TARGET_FPS = 30;
	final float TARGET_TIME_BETWEEN_RENDERS = 1f / (float) TARGET_FPS;

	public SurfaceThread(SurfaceHolder surfaceHolder, GameSurfaceView panel) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;
	}

	public void setRunning(boolean run) {
		_run = run;
	}

	@Override
	public void run() {
		Canvas c;

		// Store the last time we rendered.
		float lastRenderTime = System.nanoTime() / 1000000000.0f;
		int fps = 0;
		float lastFpsUpdated = System.nanoTime() / 1000000000.0f;

		while (_run) { // When setRunning(false) occurs, _run is
			c = null; // set to false and loop ends, stopping thread

			try {
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {

					float now = System.nanoTime() / 1000000000.0f;
					float delta = now - lastRenderTime;

					_panel.drawFrame(c, delta);
					fps++;
					lastRenderTime = now;

					if (now - lastFpsUpdated > 1f) {
						System.out.println("FPS :: " + fps);
						fps = 0;
						lastFpsUpdated = System.nanoTime() / 1000000000.0f;
					}

					// Yield until it has been at least the target time between
					// renders. This saves the CPU from hogging.
					while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS) {
						Thread.yield();

						// This stops the app from consuming all your CPU. It
						// makes this slightly less accurate, but is worth it.
						// You can remove this line and it will still work
						// (better), your CPU just climbs on certain OSes.
						// FYI on some OS's this can cause pretty bad
						// stuttering. Scroll down and have a look at different
						// peoples' solutions to this.

						Thread.sleep(1);

						now = System.nanoTime() / 1000000000.0f;
					}

				}

			} catch (InterruptedException e) {
				e.printStackTrace();

			} finally {
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

}
