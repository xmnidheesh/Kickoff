
package com.fifa.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fifa.constants.Constants;
import com.fifa.db.DbHelper;

public class VenueDao implements Constants {

    private DbHelper dbHelper;

    private static final String TAG = VenueDao.class.getSimpleName();

    private SQLiteDatabase db = null;

    String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ")
            .append(Venue.DATABASE_TABLE).toString();

    public VenueDao() {

        dbHelper = DbHelper.getInstance();

    }

    public List<com.fifa.holder.Venue> getAllVenues() {

        List<com.fifa.holder.Venue> venueList = new ArrayList<com.fifa.holder.Venue>();

        synchronized (DbHelper.lock) {

            db = dbHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery(new StringBuffer(SELECT_ALL_ROWS).toString(), null);

            try {

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        com.fifa.holder.Venue venue = new com.fifa.holder.Venue();

                        venue.setVenue_fullname(cursor.getString(0));
                        venue.setVenue_dimention(cursor.getString(1));
                        venue.setVenue_owner(cursor.getString(2));
                        venue.setVenue_surface(cursor.getString(3));
                        venue.set_id(cursor.getString(4));
                        venue.setVenue_name(cursor.getString(5));
                        venue.setVenue_des(cursor.getString(6));
                        venue.setVenue_capacity(cursor.getString(7));
                        venue.setVenue_image(cursor.getString(10));
                        venue.setVenue_lati(cursor.getString(8));
                        venue.setVenue_long(cursor.getString(9));
                        venue.setVenue_city(cursor.getString(11));
                        venue.setOpened(cursor.getString(12));
                        venue.setVenue_website(cursor.getString(13));

                        venueList.add(venue);

                    }

                }

            } catch (Exception e) {
                Log.e(TAG, "Exception while database operation", e);

            } finally {

                cursor.close();
                db.close();

            }

            return venueList;
        }

    }

}
