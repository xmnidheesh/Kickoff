package com.jn.kickoff;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.constants.SoundStatus;

public class SettingsPageSharedPreference {

	public static final String PREF_NAME = "Settings";
	private static final String tag = "SettingsPageSharedPreference";

	/**
	 * Sets on or off status for sound in the preferences.
	 * 
	 * @param context
	 * @param status
	 */

	public static void putSounds(Context context, SoundStatus status) {

		SharedPreferences settings = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.SettingsConstants.SOUND_KEY, status.getType());
		editor.commit();

			Log.e(tag, "Inserted into the shared preference" + status.getType());

	}

	/**
	 * Gets the current sound status from the preferences.
	 * 
	 * @param context
	 * @return
	 */
	public static SoundStatus getSounds(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);

		int sound = SoundStatus.SOUND_ON.getType();
		SoundStatus soundStatus = SoundStatus.valueOf(settings.getInt(
				Constants.	SettingsConstants.SOUND_KEY, sound));

			Log.e(tag, "Sounnd status from the preference" + soundStatus.name()
					+ "sound status :" + soundStatus.getType());

		return soundStatus;
	}


}
