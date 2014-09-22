package com.app.surfacegame.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.app.surfacegame.gameutils.GameSurfaceView;
import com.app.surfacegame.implementation.GameAssets;
import com.app.surfacegame.implementation.GameScreen;

public class GameFragment extends Fragment implements OnTouchListener {

	GameSurfaceView surfaceView;	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		surfaceView = new GameSurfaceView(getActivity());
		surfaceView.setOnTouchListener(this);
		surfaceView.setScreen(new GameScreen(getActivity(), surfaceView));		
		return surfaceView;
	}

	@Override
	public void onPause() {
		if (surfaceView != null)
			surfaceView.pause();
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		GameAssets.getInstance(getActivity(), true).recycleAssets();
		super.onDestroy();
	}

	@Override
	public void onResume() {
		if (surfaceView != null)
			surfaceView.resume();
		super.onResume();

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return surfaceView.onTouch(v, event);
	}

}
