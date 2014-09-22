package com.app.surfacegame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.app.surfacegame.fragments.GameFragment;

public class GameActivity extends FragmentActivity {

	FrameLayout gameContainer;
	FragmentManager fm;
	Fragment gameFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);
		bindViews();

	}

	private void bindViews() {
		gameContainer = (FrameLayout) findViewById(R.id.game_container);
		initializeViews();
	}

	private void initializeViews() {

		fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		gameFragment = new GameFragment();
		ft.add(R.id.game_container, gameFragment);
		ft.commit();
		fm.executePendingTransactions();
	}

}
