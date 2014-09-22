package com.app.surfacegame.gameutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;

public abstract class AssetsLoader {

	private Activity mActivity;

	private OnAssetsLoadListener mListener;

	private AssetManager assets;
	private SoundPool soundPool;
	private boolean isAssetsLoaded;

	public void setOnAssetsLoadListener(OnAssetsLoadListener listener) {
		this.mListener = listener;
	}

	public AssetsLoader(Activity activity, boolean isLoadAssets) {
		this.mActivity = activity;
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		this.isAssetsLoaded = false;
		if (isLoadAssets)
			loadAssetsAsync();
	}

	public void loadAssetsAsync() {
		new LoadAssetsAsync().execute();
	}


	public Activity getActivity() {
		return mActivity;
	}
		

	public void loadAssets() throws IOException, JSONException {
	}

	public void recycleAssets() {
		isAssetsLoaded = false;
	}

	private class LoadAssetsAsync extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {
				loadAssets();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			if (mListener != null) {
				if (result) {
					mListener.OnAssetsLoaded();
				} else {
					mListener.OnAssetsLoadError();
				}
			}
			isAssetsLoaded = result;
			super.onPostExecute(result);
		}

	}

	public boolean isAssetsLoaded() {
		return isAssetsLoaded;
	}

	protected HashMap<String, TextureRegion> getTextureRegion(String path)
			throws IOException, JSONException {

		HashMap<String, TextureRegion> data = new HashMap<String, TextureRegion>();

		JSONObject json = getJSON(path);
		JSONObject frame = json.getJSONObject("frames");

		Iterator<?> keys = frame.keys();

		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (frame.get(key) instanceof JSONObject) {

				JSONObject jsonSub = frame.getJSONObject(key);
				JSONObject frameSub = jsonSub.getJSONObject("frame");
				TextureRegion texture = new TextureRegion(frameSub.optInt("x"),
						frameSub.optInt("y"), frameSub.optInt("w"),
						frameSub.optInt("h"));
				data.put(key, texture);

			}
		}
		return data;

	}

	protected JSONObject getJSON(String filePath) throws IOException,
			JSONException {

		InputStream in = null;
		JSONObject jObj = null;
		String json = "";

		in = mActivity.getAssets().open(filePath);

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in), 128);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		jObj = new JSONObject(json);

		// return JSON String
		return jObj;
	}

	protected Bitmap getBitmap(String filename) throws IOException {

		AssetManager assetManager = mActivity.getAssets();
		InputStream istr;
		Bitmap bitmap = null;
		istr = assetManager.open(filename);
		bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;

	}

	protected Music getMusic(String filename) throws IOException {
		AssetFileDescriptor assetDescriptor = assets.openFd(filename);
		return new Music(assetDescriptor);

	}

	protected Sound getSound(String filename) throws IOException {
		AssetFileDescriptor assetDescriptor = assets.openFd(filename);
		int soundId = soundPool.load(assetDescriptor, 0);
		return new Sound(soundPool, soundId);
	}

	public interface OnAssetsLoadListener {

		public void OnAssetsLoaded();

		public void OnAssetsLoadError();

	}

}
