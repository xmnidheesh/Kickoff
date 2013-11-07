
package com.jn.kickoff.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jn.kickoff.constants.Constants;
import com.jn.kickoff.db.DbHelper;
import com.jn.kickoff.entity.PlayerProfile;

public class PlayerProfileDao implements Constants.PlayerProfile {

    private DbHelper dbHelper;

    private static final String TAG = PlayerProfileDao.class.getSimpleName();

    private SQLiteDatabase db = null;

    String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ").append(DATABASE_TABLE)
            .append(" where ").toString();

    public PlayerProfileDao() {

        dbHelper = DbHelper.getInstance();

    }

    public void insertIntoPlayerProfile(PlayerProfile playerProfile) {

        synchronized (DbHelper.lock) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                Log.i(TAG, "going to insert into playerprofile :");
                db.beginTransaction();

                ContentValues values = new ContentValues();
                values.put(PLAYER_ID, playerProfile.getPlayerId());
                values.put(COUNTRY_ID, playerProfile.getCountryId());

                values.put(PLAYER_FNAME, playerProfile.getFirstname());
                values.put(PLAYER_LNAME, playerProfile.getLastname());
                values.put(PLAYER_IMAGE_LINK, playerProfile.getImageLink());
                values.put(PLAYER_NATIONALITY, playerProfile.getNationality());
                values.put(PLAYER_DOB, playerProfile.getDateOfBirth());
                values.put(PLAYER_AGE, playerProfile.getAge());
                values.put(PLAYER_COUNTRY, playerProfile.getCountry());
                values.put(PLAYER_PLACE, playerProfile.getPlace());
                values.put(PLAYER_POSTION, playerProfile.getPosition());
                values.put(PLAYER_HEIGHT, playerProfile.getHeight());
                values.put(PLAYER_WEIGHT, playerProfile.getWeight());
                values.put(PLAYER_FOOT, playerProfile.getFoot());
                values.put(Constants.UPDATED_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                // Inserting Row
                db.insert(DATABASE_TABLE, null, values);

                db.setTransactionSuccessful();
                Log.i(TAG, "successfully inserted into playerProfile :");

            } catch (SQLException e) {
                Log.e(TAG, "SQLException :", e);

            } finally {
                db.endTransaction();
                db.close();
            }
        }

    }

    public PlayerProfile getAllPlayerProfileData(String playerId) {
        
        PlayerProfile PlayerProfile = null;

        synchronized (DbHelper.lock) {

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(new StringBuffer(SELECT_ALL_ROWS).append(PLAYER_ID)
                    .append(" = ").append("'").append(playerId).append("'").toString(), null);

            try {

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        PlayerProfile = new PlayerProfile();

                        PlayerProfile.setPlayerId(cursor.getString(cursor.getColumnIndex(PLAYER_ID)));
                        PlayerProfile.setCountryId(cursor.getString(cursor.getColumnIndex(COUNTRY_ID)));
                        
                        PlayerProfile.setFirstname(cursor.getString(cursor.getColumnIndex(PLAYER_FNAME)));
                        PlayerProfile.setLastname(cursor.getString(cursor.getColumnIndex(PLAYER_LNAME)));
                        PlayerProfile.setImageLink(cursor.getString(cursor.getColumnIndex(PLAYER_IMAGE_LINK)));
                        PlayerProfile.setNationality(cursor.getString(cursor.getColumnIndex(PLAYER_NATIONALITY)));
                        PlayerProfile.setDateOfBirth(cursor.getString(cursor.getColumnIndex(PLAYER_DOB)));
                        PlayerProfile.setAge(cursor.getString(cursor.getColumnIndex(PLAYER_AGE)));
                        PlayerProfile.setCountry(cursor.getString(cursor.getColumnIndex(PLAYER_COUNTRY)));
                        PlayerProfile.setPlace(cursor.getString(cursor.getColumnIndex(PLAYER_PLACE)));
                        PlayerProfile.setPosition(cursor.getString(cursor.getColumnIndex(PLAYER_POSTION)));
                        PlayerProfile.setHeight(cursor.getString(cursor.getColumnIndex(PLAYER_HEIGHT)));
                        PlayerProfile.setWeight(cursor.getString(cursor.getColumnIndex(PLAYER_WEIGHT)));
                        PlayerProfile.setFoot(cursor.getString(cursor.getColumnIndex(PLAYER_FOOT)));
                        PlayerProfile.setUpdatedDate(cursor.getString(cursor.getColumnIndex(Constants.UPDATED_DATE)));

                    }

                }

            } catch (Exception e) {
                Log.e(TAG, "Exception while database operation", e);

            } finally {

                cursor.close();
                db.close();

            }

            return PlayerProfile;

        }

    }

}
