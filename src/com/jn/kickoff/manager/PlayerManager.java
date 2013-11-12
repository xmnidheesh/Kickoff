package com.jn.kickoff.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jn.kickoff.constants.WebResponseConstants;
import com.jn.kickoff.holder.FifaPlayerDetails;
import com.jn.kickoff.webservice.AsyncTaskCallBack;
import com.jn.kickoff.webservice.FifaRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class PlayerManager {

	public static final String TAG = PlayerManager.class.getSimpleName();

	public List<FifaPlayerDetails> getAlluserRanking(final Context activity,
			final AsyncTaskCallBack asyncTaskCallBack, final int requestCode) {

		FifaRestClient.get(
				com.jn.kickoff.constants.Constants.AppConstants.URL_RATING, null,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {

						Log.i(TAG, "response : " + response);
					}

					@Override
					public void onFailure(Throwable tr, String response) {
            			Log.i(TAG, "Error from  response : " + response + " "
            					+ tr);
            				
            				if (asyncTaskCallBack == null) {
            				    Log.i(TAG, "asyncTaskCallBack is null : ");
            				    
            				} else {
            				    
            					Log.e(TAG, "in onfailure ");
            				    asyncTaskCallBack.onFinish(0,
            					    response);
            				}
            				
            				//Toast.makeText(activity, ""+response, Toast.LENGTH_SHORT).show();
            			    }

					@Override
					public void onFinish() {
						Log.i(TAG,
								"finished api call of getCategories function");

					}

					@Override
					public void onSuccess(int code, String response) {
						Log.i(TAG, "response : " + response);
						Log.i(TAG, "response code : " + code);

						try {

							if (code == WebResponseConstants.ResponseCode.OK) {
								Gson gson = new Gson();
								
								 List<FifaPlayerDetails> playerList=new ArrayList<FifaPlayerDetails>();

								java.lang.reflect.Type listTypes = new TypeToken<List<FifaPlayerDetails>>() {
								}.getType();

								playerList = gson.fromJson(response,
										listTypes);

								
								
								Log.i(TAG, "fifaPlayerDetails object: "
										+ playerList);

								if (asyncTaskCallBack == null) {
									Log.i(TAG, "asyncTaskCallBack is null : ");
								} else {
									asyncTaskCallBack.onFinish(requestCode,
											playerList);
								}
							} else if (code == WebResponseConstants.ResponseCode.UN_AUTHORIZED
									|| code == WebResponseConstants.ResponseCode.UN_SUCCESSFULL) {

								Log.i(TAG, "code  " + code);

							} else {

								Log.e(TAG, "Unexpected response code " + code);
								Log.e(TAG,
										"Expected codes "
												+ WebResponseConstants.ResponseCode.OK
												+ WebResponseConstants.ResponseCode.UN_AUTHORIZED
												+ WebResponseConstants.ResponseCode.UN_SUCCESSFULL);

							}

						} catch (JsonSyntaxException jse) {

							Log.e(TAG, "Json SyntaxException occured  ", jse);

						}

						catch (JsonParseException jpe) {
							Log.e(TAG, "Json parse exception occured  ", jpe);
						} catch (Exception e) {
							Log.e(TAG, "Exception: ", e);
						}

					}

					@Override
					public void onStart() {

						Log.i(TAG,
								"Enters into manager and calling People ranking function");

					}

				});
		return null;

	}

}
