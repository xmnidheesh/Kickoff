
package com.jn.kickoff.manager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.entity.Country;
import com.jn.kickoff.entity.Squard;
import com.jn.kickoff.holder.Fixture;
import com.jn.kickoff.utils.Util;
import com.jn.kickoff.utils.UtilValidate;

public class CountryManager implements Constants.Country {

    public static final String TAG = CountryManager.class.getSimpleName();

    private List<Country> countryList;
    private List<Fixture> fixtureList;

    public List<Country> scrapUrlForCountriesRank(String urls) {

        try {

            countryList = new ArrayList<Country>();

            String userAgent = "Mozilla";

            Response response = Jsoup.connect(urls).method(Method.POST).followRedirects(false)
                    .userAgent(userAgent).execute();

            // This will get you cookies
            Map<String, String> loginCookies = response.cookies();

            Document scrappedDoc = Jsoup.connect(urls).cookies(loginCookies).userAgent(userAgent)
                    .get();

            if (UtilValidate.isNotNull(scrappedDoc)) {

                Util.filterHtml(scrappedDoc);

                Elements elements = scrappedDoc
                        .select("table[class=leaguetable table fifa_rankings]");

                if (UtilValidate.isNotNull(elements)) {

                    Elements tHead = elements.select("thead");
                    tHead.remove();

                    for (Element element : elements.select("tr")) {

                        Country country = new Country();

                        String rank = element.select("td[class=rank]").text();

                        if (UtilValidate.isNotNull(rank))
                            country.setRank(rank);

                        String points = element.select("td[class=points]").text();

                        if (UtilValidate.isNotNull(rank))
                            country.setPoints(points);

                        Elements teamElements = element.select("td[class=text team]");

                        if (UtilValidate.isNotNull(teamElements)) {

                            country.setName(teamElements.text());

                            Elements linkElements = teamElements.select("a");

                            if (UtilValidate.isNotNull(linkElements)) {

                                String teamLink = linkElements.attr("href");
                                StringBuffer sb = new StringBuffer("");
                                sb.append(COUNTRY_LINK).append(teamLink);
                                country.setCountryLink(sb.toString());

                            }

                        }

                        countryList.add(country);

                    }

                }

            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Exception occured while parsing url :", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "IOException :", e);
        }
        return countryList;

    }

    public List<Squard> scrapSquardFromTeamLink(String link) {
        
        List<Squard> squardList = new ArrayList<Squard>();

        String userAgent = "Mozilla";
        
        Log.e(TAG,"link :"+link);

        Response response;
        try {
            response = Jsoup.connect(link).method(Method.POST).followRedirects(false)
                    .userAgent(userAgent).execute();
            

            // This will get you cookies
            Map<String, String> loginCookies = response.cookies();

            Document countrySquardDoc = Jsoup.connect(link).cookies(loginCookies)
                    .userAgent(userAgent).get();
            
            if (UtilValidate.isNotNull(countrySquardDoc)) {

                Util.filterHtml(countrySquardDoc);

                Elements squardElements = countrySquardDoc
                        .select("table[class=table squad sortable]");
                

                if (UtilValidate.isNotNull(squardElements)) {
                    
                                        
                        for (Element table : squardElements.select("tbody")) {
                            
                            for (Element tr : table.select("tr")){
                                
                                Log.e(TAG, "********** :" + tr.select("a").attr("href"));
                            }
                            
                            
                    }

                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return squardList;
    }
    
    
    
    
    public List<Fixture> scrapUrlForFixtures(String urls) {

        try {

        	fixtureList = new ArrayList<Fixture>();

            String userAgent = "Mozilla";

            Response response = Jsoup.connect(urls).method(Method.POST).followRedirects(false)
                    .userAgent(userAgent).execute();
            // This will get you cookies
            Map<String, String> loginCookies = response.cookies();

            Document scrappedDoc = Jsoup.connect(urls).cookies(loginCookies).userAgent(userAgent)
                    .get();


            if (UtilValidate.isNotNull(scrappedDoc)) {

                Util.filterHtml(scrappedDoc);
                Element elements = scrappedDoc
                        .select("table[class=fixture]").first();
                
                Log.e(TAG, "elements************** : "+elements);

                
                
                if (UtilValidate.isNotNull(elements)) {
                    
                    
                    for (Element table : elements.select("tbody")) {
                        
                        for (Element element : table.select("tr")){

                        	Fixture fixture = new Fixture();

                            String time = element.select("td[class=l dt]").text();
                            Log.e(TAG, "time************** : "+time);

                            if (UtilValidate.isNotNull(time))
                            	fixture.setDate(time);

                            String team_a = element.select("td[class=l homeTeam]").text();
                            Log.e(TAG, "team_a************** : "+team_a);


                            if (UtilValidate.isNotNull(team_a))
                            	fixture.setTeam_a(team_a);

                            
                            
                            
                            String team_b = element.select("td[class=r awayTeam]").text();
                            Log.e(TAG, "team_b************** : "+team_b);

                            if (UtilValidate.isNotNull(team_b))
                            	fixture.setTeam_b(team_b);
                            
                            
                           /* String result = element.select("td[class=r awayTeam]").text();
                            Log.e(TAG, "team_b************** : "+team_b);

                            if (UtilValidate.isNotNull(result))
                            	fixture.setResult(result);*/
                            
                            String team_a_image = element.select("a").select("img").attr("src");
                            Log.e(TAG, "team_a_image************** : "+team_a_image);

                            if (UtilValidate.isNotNull(team_a_image))
                            	fixture.setTeam_a_image(team_a_image);
                            
                            
                            String team_b_image = element.select("img").attr("src");
                            Log.e(TAG, "team_b_image************** : "+team_b_image);

                            if (UtilValidate.isNotNull(team_b_image))
                            	fixture.setTeam_b_image(team_b_image);
                            
                            
                            
                            
                            
                            
                            
                            


                            fixtureList.add(fixture);

                        }
                        
                        
                }

            }
                

            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Exception occured while parsing url :", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "IOException :", e);
        }
        return fixtureList;

    }

}
