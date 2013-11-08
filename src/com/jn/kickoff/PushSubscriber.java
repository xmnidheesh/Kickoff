package com.jn.kickoff;

import android.content.Context;

import com.jn.kickoff.activity.PushNotificationActivity;
import com.parse.PushService;

public class PushSubscriber {

	private Context context;

	public PushSubscriber(Context context) {

		this.context = context;
	}

	/**
	 * Subscribe to Channel for push notifications
	 */
	public void subscribe() {

		PushService.subscribe(context, "ShopBitesAndroid",
				PushNotificationActivity.class);

	}

	/**
	 * UnSubscribe to Channel for push notifications
	 */

	public void unSubscribe() {

		PushService.unsubscribe(context, "ShopBitesAndroid");

	}
}
