package com.fifa.webservice;

public interface AsyncTaskCallBack {

    public void onFinish(int requestCode);

    public void onFinish(int responseCode, Object result);

    public void onFinish(int responseCode, String result);

    public void onFinish(int responseCode, Object result, int requestCode);

    public void onFinish(int responseCode, Object result, boolean isPaginated);

}
