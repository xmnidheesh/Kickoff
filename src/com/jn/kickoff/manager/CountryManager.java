
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
import com.jn.kickoff.utils.Util;
import com.jn.kickoff.utils.UtilValidate;

public class CountryManager implements Constants.Country {

    public static final String TAG = CountryManager.class.getSimpleName();

    private List<Country> countryList;

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
        
        Squard squard = null;

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

                Element squardElements = countrySquardDoc.select(
                        "table[class=table squad sortable]").first();

                if (UtilValidate.isNotNull(squardElements)) {

                    for (Element table : squardElements.select("tr")) {

                        Elements cells = table.select("td");

                        for (Element element : cells) {

                            if (UtilValidate.isNotNull(element)) {

                                if (UtilValidate.isNotNull(element.select("a"))) {

                                    String profileLink = element.select("a").attr("href");
                                    
                                    Element profileImageATag = element.select("a").select("img").first();
                                    
                                    if (UtilValidate.isNotNull(profileImageATag)) {
                                        
                                        squard = new Squard();

                                        String profileImage = profileImageATag.attr("src");
                                        squard.setImage(profileImage);
                                        
                                        StringBuffer sb = new StringBuffer("");
                                        sb.append(COUNTRY_LINK).append(profileLink);
                                        squard.setProfileLink(sb.toString());

                                        squardList.add(squard);

                                    }

                                }else if(UtilValidate.isNotNull(element.select("div"))&&
                                        UtilValidate.isNotNull(element.select("div").select("a"))){
                                    
                                    squard.setName(element.select("div").text());
                                    
                                    Log.e(TAG, "name " + squard.getName());
                                    
                                }

                                /*
                                 * if
                                 * (UtilValidate.isNotNull(element.select("div"
                                 * ))){ Element div =
                                 * element.select("div").first();
                                 * if(UtilValidate.isNotNull(div)){
                                 * if(UtilValidate.isNotNull(div.select("a"))){
                                 * String profileLink =
                                 * div.select("a").attr("href"); String
                                 * playerName = div.select("a").text();
                                 * squard.setProfileLink(profileLink);
                                 * squard.setName(playerName); Log.e(TAG,
                                 * "profileLink :" + profileLink); Log.e(TAG,
                                 * "playerName :" + playerName); } } }
                                 */

                            }
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

    private void scrapPlayerName() {

    }
}
