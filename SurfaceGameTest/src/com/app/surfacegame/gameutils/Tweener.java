package com.app.surfacegame.gameutils;


public class Tweener {
	
	private final float startValue;
	private final float endValue;
	private final float duration;
	private final boolean isLooping;
	private int isIncrementing;
	
	private float tempValue;	
	private boolean isComplete;	
	private int count;
	private onTweeningComplete mListener;
	

	public Tweener(float startValue, float endValue, float duration, boolean isLooping) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.duration = duration;
		this.isComplete = true;
		this.isLooping = isLooping;
		this.count = 0;
		
		if (this.startValue <= this.endValue)
			this.isIncrementing = 1;
		else
			this.isIncrementing = -1;
		
		this.tempValue = startValue;
		
	}
	
	public void setOnTweeningCompleteListener (onTweeningComplete listener) {
		this.mListener = listener;
	}
	
	public void start () {
		this.isComplete = false;
		count = 1;
		this.tempValue = startValue;
	}
	
	public float getValue(float deltaTime) {
		
		if (!isComplete) {			
			if ((isIncrementing * this.tempValue) < (isIncrementing * endValue)) {
				this.tempValue +=  ((endValue - startValue)/duration) * deltaTime;
			}
			else  {
				this.tempValue = endValue;
				
				if (isLooping) {
					restart();
				} else {
					isComplete = true;
					if(mListener != null) {
						mListener.onAnimationComplete();
					}
				}
				
			}
		}
		return this.tempValue;
	}
	
	public void restart () {
		isComplete = false;
		count++;
		this.tempValue = startValue;
	}
	
	public boolean isAnimComplete() {
		return this.isComplete;
	}
	
	public int getCount () {
		return this.count;
	}
	
	public interface onTweeningComplete {
		
		public void onAnimationComplete();
	}
	
}
