package com.app.surfacegame.gameutils;

import aurelienribon.tweenengine.TweenAccessor;

public class GameObjectAccessor implements TweenAccessor<GameObject> {

	public static final int X = 1;
	public static final int Y = 2;
	public static final int XY = 3;
	public static final int HEIGHT = 4;
	public static final int WIDTH = 5;
	public static final int SCALE = 6;
	public static final int ROTATE = 7;
	public static final int ALPHA = 8;

	@Override
	public int getValues(GameObject target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case X:
			returnValues[0] = target.position.x;
			return 1;
		case Y:
			returnValues[0] = target.position.y;
			return 1;
		case XY:
			returnValues[0] = target.position.x;
			returnValues[1] = target.position.y;
			return 2;
		case HEIGHT:
			returnValues[0] = target.bounds.height;
			return 1;
		case WIDTH:
			returnValues[0] = target.bounds.width;
			return 1;
		case SCALE:
			returnValues[0] = target.bounds.width;
			returnValues[1] = target.bounds.height;
			return 2;
		case ROTATE:
			returnValues[0] = target.angle;
			return 1;
		case ALPHA:
			returnValues[0] = target.alpha;
			return 1;

		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(GameObject target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case X:
			target.position.set(newValues[0], target.position.y);
			target.updateBounds();
			break;
		case Y:
			target.position.set(target.position.x, newValues[0]);
			target.updateBounds();
			break;
		case XY:
			target.position.x = newValues[0];
			target.position.y = newValues[1];
			target.updateBounds();
			break;
		case HEIGHT:
			target.bounds.height = newValues[0];
			target.updateBounds();
			break;
		case WIDTH:
			target.bounds.width = newValues[0];
			target.updateBounds();
			break;
		case SCALE:
			target.bounds.width = newValues[0];
			target.bounds.height = newValues[1];
			target.updateBounds();
			break;
		case ROTATE:
			target.angle = newValues[0];
			break;
		case ALPHA:
			target.alpha = newValues[0];
			break;
		default:
			assert false;
			break;
		}
	}

}
