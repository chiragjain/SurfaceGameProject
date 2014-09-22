package com.app.surfacegame.gameutils;

public class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	public float alpha;
	public float angle;
	public final Vector2 anchor;

	public GameObject(float x, float y, float width, float height, float angle,
			float alpha) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width,
				height);
		this.alpha = alpha;
		this.anchor = new Vector2(x, y);
	}

	public GameObject(float x, float y, float width, float height) {
		this(x, y, width, height, 0, 1);
	}

	public void setAnchor(float x, float y) {
		this.anchor.set(x, y);
	}

	public void updateBounds() {
		this.bounds.upperLeft.set(position.x - bounds.width / 2, position.y
				- bounds.height / 2);
	}

}
