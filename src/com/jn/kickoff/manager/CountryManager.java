
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
                            
                            Log.e(TAG, "country :" + country.getCountryLink());

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

}
