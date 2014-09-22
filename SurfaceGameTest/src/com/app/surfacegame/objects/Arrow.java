package com.app.surfacegame.objects;

import com.app.surfacegame.gameutils.DynamicGameObject;

public class Arrow extends DynamicGameObject {
	
	public float stateTime;

	public Arrow(float x, float y, float width, float height) {
		super(x, y, width, height);	
		this.velocity.set(0, 50);
		this.stateTime = 0;
	}
	
	@Override	
	public void update(float deltaTime) {
		if (stateTime >= 4f) {
			velocity.set(50, 0);
		}
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		updateBounds();
		stateTime += deltaTime;
	}

}
