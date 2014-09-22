package com.app.surfacegame.gameutils;

public abstract class DynamicGameObject extends GameObject{
	public final Vector2 velocity;
	public final Vector2 accelaration;

	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accelaration = new Vector2();
	}

	public DynamicGameObject(float x, float y, float width, float height,
			float angle, float alpha) {
		super(x, y, width, height, angle, alpha);
		velocity = new Vector2();
		accelaration = new Vector2();
	}	
	
	public abstract void update (float deltaTime);
}
