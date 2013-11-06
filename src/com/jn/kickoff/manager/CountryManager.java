
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
import com.jn.kickoff.entity.PlayerProfile;
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

                                    Element profileImageATag = element.select("a").select("img")
                                            .first();

                                    if (UtilValidate.isNotNull(profileImageATag)) {

                                        squard = new Squard();

                                        String profileImage = profileImageATag.attr("src");
                                        squard.setImage(profileImage);

                                        StringBuffer sb = new StringBuffer("");
                                        sb.append(COUNTRY_LINK).append(profileLink);
                                        squard.setProfileLink(sb.toString());

                                        squardList.add(squard);

                                    }

                                } else if (UtilValidate.isNotNull(element.select("div"))
                                        && UtilValidate
                                                .isNotNull(element.select("div").select("a"))) {

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
                Element elements = scrappedDoc.select("table[class=fixture]").first();

                Log.e(TAG, "elements************** : " + elements);

                if (UtilValidate.isNotNull(elements)) {

                    for (Element table : elements.select("tbody")) {

                        for (Element element : table.select("tr")) {

                            Fixture fixture = new Fixture();

                            String time = element.select("td[class=l dt]").text();
                            Log.e(TAG, "time************** : " + time);

                            if (UtilValidate.isNotNull(time))
                                fixture.setDate(time);

                            String team_a = element.select("td[class=l homeTeam]").text();
                            Log.e(TAG, "team_a************** : " + team_a);

                            if (UtilValidate.isNotNull(team_a))
                                fixture.setTeam_a(team_a);

                            String team_b = element.select("td[class=r awayTeam]").text();
                            Log.e(TAG, "team_b************** : " + team_b);

                            if (UtilValidate.isNotNull(team_b))
                                fixture.setTeam_b(team_b);

                            /*
                             * String result =
                             * element.select("td[class=r awayTeam]").text();
                             * Log.e(TAG, "team_b************** : "+team_b); if
                             * (UtilValidate.isNotNull(result))
                             * fixture.setResult(result);
                             */

                            String team_a_image = element.select("a").select("img").attr("src");
                            Log.e(TAG, "team_a_image************** : " + team_a_image);

                            if (UtilValidate.isNotNull(team_a_image))
                                fixture.setTeam_a_image(team_a_image);

                            String team_b_image = element.select("img").attr("src");
                            Log.e(TAG, "team_b_image************** : " + team_b_image);

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

    public PlayerProfile scrapPlayerprofileDetails(String link) {

        PlayerProfile playerProfile = null;

        Log.e(TAG, "In scrapPlayerprofileDetails");

        String userAgent = "Mozilla";

        Response response;
        
        try {
            response = Jsoup.connect(link).method(Method.POST).followRedirects(false)
                    .userAgent(userAgent).execute();

            // This will get you cookies
            Map<String, String> loginCookies = response.cookies();

            Document playerProfileDoc = Jsoup.connect(link).cookies(loginCookies)
                    .userAgent(userAgent).get();

            if (UtilValidate.isNotNull(playerProfileDoc)) {

                Util.filterHtml(playerProfileDoc);

                Elements profileElements = playerProfileDoc
                        .select("div[id=page_player_1_block_player_passport_4]");

                if (UtilValidate.isNotNull(profileElements)) {

                    String[] str = new String[20];

                    int i = 0;

                    for (Element dd : profileElements.select("dl").select("dd")) {

                        str[i] = dd.text();
                        i++;
                    }

                    playerProfile = new PlayerProfile();

                    playerProfile.setFirstname(str[0]);

                    playerProfile.setLastname(str[1]);

                    playerProfile.setNationality(str[2]);

                    playerProfile.setDateOfBirth(str[3]);

                    playerProfile.setAge(str[4]);

                    playerProfile.setCountry(str[5]);

                    playerProfile.setPlace(str[6]);

                    playerProfile.setPosition(str[7]);

                    playerProfile.setHeight(str[8]);

                    playerProfile.setWeight(str[9]);

                    playerProfile.setFoot(str[10]);

                }

            }

            return playerProfile;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return playerProfile;
    }
}
