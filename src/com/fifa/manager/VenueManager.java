
package com.fifa.manager;

import java.util.List;

import com.fifa.dao.VenueDao;

public class VenueManager {

    public List<com.fifa.holder.Venue> getAllVenuesFromTable() {

        VenueDao venueDao = new VenueDao();
        return venueDao.getAllVenues();
    }

}
