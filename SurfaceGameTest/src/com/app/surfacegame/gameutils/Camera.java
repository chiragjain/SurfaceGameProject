package com.app.surfacegame.gameutils;

import android.graphics.RectF;

public class Camera {

	private int cameraWidth;
	private int cameraHeight;

	private int containerWidth;
	private int containerHeight;
	private RectF rectf;

	public Camera(int cameraWidth, int cameraHeight, int containerWidth,
			int containerHeight) {
		this.cameraWidth = cameraWidth;
		this.cameraHeight = cameraHeight;
		this.containerWidth = containerWidth;
		this.containerHeight = containerHeight;
		rectf = new RectF();
	}

	public float getScalingFactorX() {
		return (float) containerWidth / cameraWidth;
	}

	public float getScalingFactorY() {
		return (float) containerHeight / cameraHeight;
	}

	public Rectangle getScaledRectangle(Rectangle r) {
		return new Rectangle(r.upperLeft.x * getScalingFactorX(), r.upperLeft.y
				* getScalingFactorY(), r.width * getScalingFactorX(), r.height
				* getScalingFactorY());
	}

	public RectF getScaledRect(RectF r) {
		rectf.left = r.left * getScalingFactorX();
		rectf.top = r.top * getScalingFactorY();
		rectf.right = r.right * getScalingFactorX();
		rectf.bottom = r.bottom * getScalingFactorY();
		return rectf;
	}

	public Vector2 getScaledPoint(Vector2 p) {
		return new Vector2(p.x * getScalingFactorX(), p.y * getScalingFactorY());
	}

	public Vector2 touchToWorld(Vector2 touchPoint) {
		return new Vector2(touchPoint.x / getScalingFactorX(), touchPoint.y
				/ getScalingFactorY());
	}
}
