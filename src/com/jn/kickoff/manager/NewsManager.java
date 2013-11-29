
package com.jn.kickoff.manager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.constants.WebResponseConstants;
import com.jn.kickoff.entity.Country;
import com.jn.kickoff.entity.News;
import com.jn.kickoff.entity.NewsDetails;
import com.jn.kickoff.holder.NewsBase;
import com.jn.kickoff.utils.Util;
import com.jn.kickoff.utils.UtilValidate;
import com.jn.kickoff.webservice.AsyncTaskCallBack;
import com.jn.kickoff.webservice.FifaRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NewsManager implements Constants {

    public static final String TAG = NewsManager.class.getSimpleName();

    public List<NewsBase> getNews(final Context activity,
            final AsyncTaskCallBack asyncTaskCallBack, final int requestCode) {

        RequestParams mapParam = new RequestParams();
        mapParam.put("apikey", "m6uqzxpqxkb9953kkucs3e6g");

        FifaRestClient.get(AppConstants.URL_NEWS, mapParam, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {

                Log.i(TAG, "response : " + response);
            }

            @Override
            public void onFailure(Throwable tr, String response) {
                Log.i(TAG, "Error from  response : " + response + " " + tr);

                if (asyncTaskCallBack == null) {
                    Log.i(TAG, "asyncTaskCallBack is null : ");

                } else {

                    Log.e(TAG, "in onfailure ");
                    asyncTaskCallBack.onFinish(0, response);
                }

                // Toast.makeText(activity, ""+response,
                // Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "finished api call of getNews function");

            }

            @Override
            public void onSuccess(int code, String response) {
                Log.i(TAG, "response : " + response);
                Log.i(TAG, "response code : " + code);

                try {
                    NewsBase newsBase = new NewsBase();

                    if (code == WebResponseConstants.ResponseCode.OK) {
                        Gson gson = new Gson();

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
                        Log.i(TAG, "requestCode  " + requestCode);
                        asyncTaskCallBack.onFinish(requestCode, newsBase);

                    } else {

                        Log.e(TAG, "Unexpected  code " + code);
                        Log.i(TAG, "Unexpected requestCode  " + requestCode);
                        Log.e(TAG, "Expected codes " + WebResponseConstants.ResponseCode.OK
                                + WebResponseConstants.ResponseCode.UN_AUTHORIZED
                                + WebResponseConstants.ResponseCode.UN_SUCCESSFULL);
                        asyncTaskCallBack.onFinish(requestCode, newsBase);

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

                Log.i(TAG, "Enters into manager and calling getNews function");

            }

        });
        return null;

    }

    public ArrayList<News> scrapUrlForNews(String urls) {

        ArrayList<News> newsList = null;

        try {

            newsList = new ArrayList<News>();

            String userAgent = "Mozilla";

            Log.e(TAG, "urls :" + urls);

            Response response = Jsoup.connect(urls).method(Method.POST).followRedirects(false)
                    .userAgent(userAgent).timeout(10 * 5000).execute();

            // This will get you cookies
            Map<String, String> loginCookies = response.cookies();

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                Log.e(TAG, "statusCode :" + statusCode);

                Document scrappedDoc = Jsoup.connect(urls).cookies(loginCookies)
                        .userAgent(userAgent).get();

                if (UtilValidate.isNotNull(scrappedDoc)) {

                    Util.filterHtml(scrappedDoc);

                    Elements elements = scrappedDoc.select("div[id=news-archive]");

                    if (UtilValidate.isNotNull(elements)) {

                        Elements firstNewsDocs = elements.select("div[class=day-news first-child]")
                                .first().children();

                        String newDate = firstNewsDocs.select("div[class=date]").text();

                        Element firstNewsUlDocs = firstNewsDocs.select("ul").first();

                        if (UtilValidate.isNotNull(firstNewsUlDocs)) {

                            for (Element liEle : firstNewsUlDocs.children()) {
                                
                                News news = new News();

                                if (UtilValidate.isNotNull(liEle)) {
                                    
                                    news.setDate(newDate);

                                    if (UtilValidate.isNotNull(liEle.select("div[class=imgBox]"))) {

                                        String imageSrc = liEle.select("div[class=imgBox]")
                                                .select("img").attr("src");

                                        if (UtilValidate.isNotNull(imageSrc))
                                            news.setNews_image_thumb(imageSrc);
                                    }

                                    Elements article = liEle.select("div[class=articleInfo]");

                                    if (UtilValidate.isNotNull(article)) {

                                        String sectionName = article.select(
                                                "span[class=sectionName]").text();

                                        if (UtilValidate.isNotNull(article))
                                            news.setSection_name(sectionName);

                                        String time = article.select("span[class=time]").text();
                                        if (UtilValidate.isNotNull(time))
                                            news.setSection_name(time);

                                        String detail_url = article.select("a").attr("href")
                                                .toString();
                                        if (UtilValidate.isNotNull(detail_url))
                                            news.setNews_detail_link(AppConstants.URL_BASE
                                                    + detail_url);

                                        String news_heading = article.select("a").text();
                                        if (UtilValidate.isNotNull(news_heading))
                                            news.setNews_heading(news_heading);

                                        Elements articleSmry = article
                                                .select("div[class=articleSummary]");
                                        if (UtilValidate.isNotNull(articleSmry)) {
                                            String summury = articleSmry.text();
                                            if (UtilValidate.isNotNull(summury))
                                                news.setNews_summury(summury);
                                        }

                                    }

                                }
                                
                                newsList.add(news);

                            }
                            
                            Log.e(TAG, "newsList size :" + newsList.size());
                            return newsList;
                        }
                    }

                }

            } else {

                Log.e(TAG, "received error code :  :" + statusCode);
                System.out.println("received error code : " + statusCode);
            }
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Exception occured while parsing url :", e);
        }catch(SocketTimeoutException e){
            
            
        }catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Exception s:", e);
        }
        return newsList;

    }
    
    
    public NewsDetails scrapUrlForNewsDetails(String urls) {

        NewsDetails newsDetails = null;

        try {

            newsDetails = new NewsDetails();

            String userAgent = "Mozilla";

            Log.e(TAG, "urls :" + urls);

            Response response = Jsoup.connect(urls).method(Method.POST).followRedirects(false)
                    .userAgent(userAgent).timeout(10 * 5000).execute();

            // This will get you cookies
            Map<String, String> loginCookies = response.cookies();

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                Log.e(TAG, "statusCode :" + statusCode);

                Document scrappedDoc = Jsoup.connect(urls).cookies(loginCookies)
                        .userAgent(userAgent).get();

                if (UtilValidate.isNotNull(scrappedDoc)) {

                    Util.filterHtml(scrappedDoc);

                    Element elements = scrappedDoc.select("div[class=top-section clearfix]").first();

                    if (UtilValidate.isNotNull(elements)) {
                        
                        Element element = elements.select("header[class=article-header default]").first();
                        
                        if (UtilValidate.isNotNull(element)) {
                            
                            String newsImageMain = element.select("img").attr("src");
                            
                            newsDetails.setNews_image_large(newsImageMain);
                            
                            Element heading = element.select("div[class=headlines]").first();
                            
                            if (UtilValidate.isNotNull(heading)) {
                                
                                String headingText = heading.select("h1").text();
                                newsDetails.setHeadin_text(headingText);
                            }
                            
                        }
                        
                        Element contentElement = scrappedDoc.select("div[class=article-content]").first();
                        
                        if (UtilValidate.isNotNull(contentElement)) {
                            
                            Element contentInnerElement = scrappedDoc.select("div[class=module module-article-body clearfix]").first();
                            
                            if (UtilValidate.isNotNull(contentInnerElement)) {
                                
                                Element contentBody = contentInnerElement.select("div[class=article-text]").first();
                                
                                if (UtilValidate.isNotNull(contentBody)) {
                                    
                                    String bodyText = contentBody.text();
                                    
                                    newsDetails.setNews_body_text(bodyText);
                                    
                                }
                                
                                
                            }
                            
                            
                        }
                        
                        
                        
                    }

                }

            } else {

                Log.e(TAG, "received error code :  :" + statusCode);
                System.out.println("received error code : " + statusCode);
            }
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Exception occured while parsing url :", e);
        }catch(SocketTimeoutException e){
            
            
        }catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Exception s:", e);
        }
        return newsDetails;

    }

}
