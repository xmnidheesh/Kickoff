package com.fifa.webservice;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FifaRestClient {

    private static final String Tag = FifaRestClient.class.getName();

    // private static final String BASE_URL =
    // "http://tvm.x-minds.info:19680/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params,
	    AsyncHttpResponseHandler responseHandler) {
	client.setBasicAuth("worldcup", "coconut", new AuthScope(
		AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM));
	client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, Context ctx,
	    AsyncHttpResponseHandler responseHandler) {

	/*
	 * UsernamePasswordCredentials credentials = new
	 * UsernamePasswordCredentials("worldcup","coconut"); Header header =
	 * BasicScheme.authenticate(credentials, "UTF-8", false); Header[]
	 * headers = {header};
	 */
	client.setBasicAuth("worldcup", "coconut", new AuthScope(
		AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM));
	// url = "http://tvm.x-minds.info:19680/api/user/profileimageupdate";
	client.post(ctx, url, params, responseHandler);

	// client.post(ctx,getAbsoluteUrl(url),headers,
	// params,DEFAULT_CONTENT_TYPE, responseHandler);
    }

    public static void post(String url, HttpEntity multipartEntity,
	    String mime, Context ctx, AsyncHttpResponseHandler responseHandler) {

	client.setBasicAuth("worldcup", "coconut", new AuthScope(
		AuthScope.ANY_HOST, 19680, AuthScope.ANY_REALM));

	client.post(ctx, url, multipartEntity, mime, responseHandler);

    }

}
