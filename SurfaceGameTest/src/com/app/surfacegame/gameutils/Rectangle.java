package com.app.surfacegame.gameutils;

import android.graphics.RectF;

public class Rectangle {
	public final Vector2 upperLeft;
	public float width, height;
	private RectF rectf;

	public Rectangle(float x, float y, float width, float height) {
		this.upperLeft = new Vector2(x, y);
		this.width = width;
		this.height = height;
		rectf = new RectF(upperLeft.x, upperLeft.y, (upperLeft.x + width),
				(upperLeft.y + height));
	}

	public Rectangle(RectF rect) {
		this.upperLeft = new Vector2(rect.left, rect.top);
		this.width = rect.width();
		this.height = rect.height();
		rectf = new RectF(upperLeft.x, upperLeft.y, (upperLeft.x + width),
				(upperLeft.y + height));
	}

	public RectF getRect() {
		rectf.left = upperLeft.x;
		rectf.top = upperLeft.y;
		rectf.right = upperLeft.x + width;
		rectf.bottom = upperLeft.y + height;
		return rectf;
	}
}
