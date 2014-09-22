package com.app.surfacegame.gameutils;

import android.graphics.Rect;

public class TextureRegion {

	public int x, y, w, h;
	private Rect rect;

	public TextureRegion(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rect = new Rect(x, y, x + w, y + h);
	}

	public Rect getRect() {
		return this.rect;
	}

	public void updateRect() {
		this.rect.left = x;
		this.rect.top = y;
		this.rect.right = x + w;
		this.rect.bottom = y + h;
	}

}
