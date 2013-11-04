package com.jn.kickoff.constants;

public interface Constants {
    
    public static final String MENU1 = "venues";
    public static final String MENU2 = "fixtures";
    public static final String MENU3 = "TopPlayers";
    public static final String MENU4 = "TopTeams";
    
    interface Venue{
        
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
    
    interface Country{
        
        String COUNTRY_RANKING_FEED_URL = "http://int.soccerway.com/teams/rankings/fifa/";
        String COUNTRY_LINK = "http://int.soccerway.com";
    }
    
    interface AppConstants
    {
        String URL_RATING="http://tools.fifaguide.com/api/topten/rating";
    }

}
