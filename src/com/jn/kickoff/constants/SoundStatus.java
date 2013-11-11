package com.jn.kickoff.constants;

public enum SoundStatus {

	NONE(-1), SOUND_ON(1), SOUND_OFF(0);

	private final int status;

	SoundStatus(int status) {
		this.status = status;
	}

	public static SoundStatus valueOf(int type) {

		for (SoundStatus _type : SoundStatus.values()) {

			if (_type.getType() == type)

				return _type;

		}

		return NONE;
	}

	public int getType() {
		return this.status;
	}

}
