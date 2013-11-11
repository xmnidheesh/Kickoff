package com.jn.kickoff;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;


public class AnimationSounds {

	private Context context;
	private MediaPlayer mediaPlayer;
	private final String Tag = "AnimationSounds";
	int keycode;

	public AnimationSounds(Context context) {

		this.context = context;

	}

	public void mediaplayerOn() {

		MediaPlayer mediaPlayer = new MediaPlayer();
		AudioManager audioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 9, 0);

		Settings.System.putInt(context.getContentResolver(),
				Settings.System.SOUND_EFFECTS_ENABLED, 1);

		Settings.System.putInt(context.getContentResolver(),
				Settings.System.VOLUME_MUSIC, 1);
		if (keycode == KeyEvent.KEYCODE_VOLUME_UP) {

			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

		}
		if (keycode == KeyEvent.KEYCODE_VOLUME_DOWN) {

			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

		}
		mediaPlayer.setVolume(1.0f, 1.0f);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

	}

	public void mediaplayerOff() {
		AudioManager audioManager1 = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager1.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
		MediaPlayer mediaPlayer1 = new MediaPlayer();
		Settings.System.putInt(context.getContentResolver(),
				Settings.System.SOUND_EFFECTS_ENABLED, 0);

		Settings.System.putInt(context.getContentResolver(),
				Settings.System.VOLUME_MUSIC, 0);

		mediaPlayer1.setVolume(0.0f, 0.0f);
		mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}

	public void animationSoundBiteOn() {
		mediaplayerOn();

		mediaPlayer = MediaPlayer.create(context, R.raw.magicbell);
		/* mediaPlayer = MediaPlayer.create(this, R.raw.bell); */
		mediaPlayer.start();

			Log.i(Tag, "Bite Sound bell on");

	}



	public void whooshSoundon() {
		mediaplayerOn();
		mediaPlayer = MediaPlayer.create(context, R.raw.whoosh);
		mediaPlayer.start();
	}

	public void whooshSoundoff() {

		mediaplayerOff();
		mediaPlayer = MediaPlayer.create(context, R.raw.whoosh);
		mediaPlayer.start();

	}
	
	
	
	public void thankYouSoundon() {
		mediaplayerOn();
		mediaPlayer = MediaPlayer.create(context, R.raw.thankyou);
		mediaPlayer.start();
	}

	public void thankYouSoundoff() {

		mediaplayerOff();
		mediaPlayer = MediaPlayer.create(context, R.raw.thankyou);
		mediaPlayer.start();

	}
}
