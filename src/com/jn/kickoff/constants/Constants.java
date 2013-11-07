
package com.jn.kickoff.constants;

public interface Constants {

    public static final String MENU1 = "venues";

    public static final String MENU2 = "fixtures";

    public static final String MENU3 = "TopPlayers";

    public static final String MENU4 = "TopTeams";

    public static final String MENU5 = "News";

    public String UPDATED_DATE = "updated_date";

    interface Venue {

        String DATABASE_TABLE = "fifa_venues";

        String VENUE_ID = "_id";

        String VENUE_NAME = "venue_name";

        String VENUE_DES = "venue_des";

        String VENUE_CAPACITY = "venue_capacity";

        String VENUE_LATI = "venue_lati";

        String VENUE_LONG = "venue_long";

        String VENUE_IMAGE = "venue_image";

        String VENUE_CITY = "venue_city";

        String VENUE_FULLNAME = "venue_fullname";

        String VENUE_DIMENTION = "venue_dimention";

        String VENUE_SURFACE = "venue_surface";

        String VENUE_OWNER = "venue_owner";

        String VENUE_FOUNDED = "venue_founded";

        String VENUE_WEBSITE = "venue_website";
    }

    interface Country {

        String COUNTRY_RANKING_FEED_URL = "http://int.soccerway.com/teams/rankings/fifa/";

        String COUNTRY_LINK = "http://int.soccerway.com";

        String DATABASE_TABLE = "country";

        String COUNTRY_ID = "_id";

        String COUNTRY_NAME = "country_name";

        String COUNTRY_RANK = "country_rank";

        String COUNTRY_POINT = "country_point";

        String COUNTRY_URL = "country_url";

    }

    interface AppConstants {
        String ADDMOB = "a14dc3594438359";

        String URL_RATING = "http://tools.fifaguide.com/api/topten/rating";

        String URL_NEWS = "http://api.espn.com/v1/sports/news/headlines/top";
    }

    interface Players {

        String DATABASE_TABLE = "players";

        String PLAYER_ID = "_id";

        String COUNTRY_ID = "country_id";

        String PLAYER_NAME = "player_name";

        String PLAYER_IMAGE_LINK = "player_image";

        String PLAYER_PROFILE_LINK = "player_profile_link";
    }

    interface PlayerProfile {

        String DATABASE_TABLE = "player_profile";

        String _ID = "_id";

        String PLAYER_ID = "p_id";

        String COUNTRY_ID = "c_id";

        String PLAYER_FNAME = "f_name";

        String PLAYER_LNAME = "l_name";

        String PLAYER_IMAGE_LINK = "i_link";

        String PLAYER_NATIONALITY = "nationality";

        String PLAYER_DOB = "dob";

        String PLAYER_AGE = "age";

        String PLAYER_COUNTRY = "country";

        String PLAYER_PLACE = "place";

        String PLAYER_POSTION = "position";

        String PLAYER_HEIGHT = "height";

        String PLAYER_WEIGHT = "weight";

        String PLAYER_FOOT = "foot";

    }

}
