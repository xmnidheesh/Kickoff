
package com.jn.kickoff.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.db.DbHelper;
import com.jn.kickoff.entity.Squard;

public class PlayerDao implements Constants.Players {

    private DbHelper dbHelper;

    private static final String TAG = PlayerDao.class.getSimpleName();

    private SQLiteDatabase db = null;

    String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ").append(DATABASE_TABLE)
            .append(" where ").toString();

    public PlayerDao() {

        dbHelper = DbHelper.getInstance();

    }

    public void insertIntoPlayers(List<Squard> players) {

        synchronized (DbHelper.lock) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                Log.i(TAG, "going to insert into players :");
                db.beginTransaction();

                for (Squard squard : players) {

                    ContentValues values = new ContentValues();
                    values.put(COUNTRY_ID, squard.getCountry_id());
                    values.put(PLAYER_NAME, squard.getName());
                    values.put(PLAYER_IMAGE_LINK, squard.getProfileImage()); 
                    values.put(PLAYER_PROFILE_LINK, squard.getProfileLink());
                    values.put(Constants.UPDATED_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    // Inserting Row
                    db.insert(DATABASE_TABLE, null, values);

                }

                db.setTransactionSuccessful();
                Log.i(TAG, "successfully inserted into players :");

            } catch (SQLException e) {
                Log.e(TAG, "SQLException :", e);

            } finally {
                db.endTransaction();
                db.close();
            }
        }

    }

    public List<Squard> getAllPlayersByCountryWise(String countryId) {

        List<Squard> playerList = new ArrayList<Squard>();

        synchronized (DbHelper.lock) {

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(new StringBuffer(SELECT_ALL_ROWS).append(COUNTRY_ID)
                    .append(" = ").append("'").append(countryId).append("'").toString(), null);

            try {

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Squard squard = new Squard();

                        Log.e(TAG,
                                "player -d :" + cursor.getString(cursor.getColumnIndex(PLAYER_ID)));
                        
                        Log.e(TAG,
                                "c -d :" + cursor.getString(cursor.getColumnIndex(COUNTRY_ID)));

                        squard.set_id(cursor.getString(cursor.getColumnIndex(PLAYER_ID)));
                        squard.setName(cursor.getString(cursor.getColumnIndex(PLAYER_NAME)));
                        squard.setProfileImage(cursor.getString(cursor
                                .getColumnIndex(PLAYER_IMAGE_LINK)));
                        squard.setProfileLink(cursor.getString(cursor
                                .getColumnIndex(PLAYER_PROFILE_LINK)));
                        squard.setCountry_id(cursor.getString(cursor.getColumnIndex(COUNTRY_ID)));

                        squard.setUpdatedDate(cursor.getString(cursor.getColumnIndex(Constants.UPDATED_DATE)));
                        
                        playerList.add(squard);

                    }

                }

            } catch (Exception e) {
                Log.e(TAG, "Exception while database operation", e);

            } finally {

                cursor.close();
                db.close();

            }

            return playerList;

        }

    }

}
