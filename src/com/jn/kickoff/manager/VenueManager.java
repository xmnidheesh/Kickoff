
package com.jn.kickoff.manager;

import java.util.List;

import com.jn.kickoff.dao.VenueDao;

public class VenueManager {

    public List<com.jn.kickoff.holder.Venue> getAllVenuesFromTable() {

        VenueDao venueDao = new VenueDao();
        return venueDao.getAllVenues();
    }

}
