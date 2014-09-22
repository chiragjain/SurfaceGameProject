package com.app.surfacegame.objects;

import com.app.surfacegame.gameutils.DynamicGameObject;

public class Beaver extends DynamicGameObject {

	public float stateTime;

	public Beaver(float x, float y) {
		super(x, y, 200, 300);
		velocity.set(0, 10);
		accelaration.y = 5;
		stateTime = 0;
	}

	public void update(float deltaTime) {
		velocity.add(accelaration.x * deltaTime, accelaration.y * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		updateBounds();
		stateTime += deltaTime;
	}

}