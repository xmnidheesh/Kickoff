
package com.jn.kickoff.manager;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.jn.kickoff.constants.WebResponseConstants;
import com.jn.kickoff.holder.NewsBase;
import com.jn.kickoff.webservice.AsyncTaskCallBack;
import com.jn.kickoff.webservice.FifaRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NewsManager {

    public static final String TAG = NewsManager.class.getSimpleName();

    public List<NewsBase> getNews(final Context activity,
            final AsyncTaskCallBack asyncTaskCallBack, final int requestCode) {

        RequestParams mapParam = new RequestParams();
        mapParam.put("apikey", "m6uqzxpqxkb9953kkucs3e6g");

        FifaRestClient.get(com.jn.kickoff.constants.Constants.AppConstants.URL_NEWS, mapParam,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {

                        Log.i(TAG, "response : " + response);
                    }

                    @Override
                    public void onFailure(Throwable tr, String response) {
                        Log.i(TAG, "Error from  response : " + response + " " + tr);
                    }

                    @Override
                    public void onFinish() {
                        Log.i(TAG, "finished api call of getCategories function");

                    }

                    @Override
                    public void onSuccess(int code, String response) {
                        Log.i(TAG, "response : " + response);
                        Log.i(TAG, "response code : " + code);

                        try {

                            if (code == WebResponseConstants.ResponseCode.OK) {
                                Gson gson = new Gson();

                                NewsBase newsBase = new NewsBase();

                                newsBase = gson.fromJson(response, NewsBase.class);

                                Log.i(TAG, "newsBase object: " + newsBase);

                                if (asyncTaskCallBack == null) {
                                    Log.i(TAG, "asyncTaskCallBack is null : ");
                                } else {
                                    asyncTaskCallBack.onFinish(requestCode, newsBase);
                                }
                            } else if (code == WebResponseConstants.ResponseCode.UN_AUTHORIZED
                                    || code == WebResponseConstants.ResponseCode.UN_SUCCESSFULL) {

                                Log.i(TAG, "code  " + code);

                            } else {

                                Log.e(TAG, "Unexpected response code " + code);
                                Log.e(TAG, "Expected codes " + WebResponseConstants.ResponseCode.OK
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

                        Log.i(TAG, "Enters into manager and calling People ranking function");

                    }

                });
        return null;

    }

}
