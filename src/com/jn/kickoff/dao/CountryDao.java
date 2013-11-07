
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
import com.jn.kickoff.entity.Country;

public class CountryDao implements Constants.Country {

    private DbHelper dbHelper;

    private static final String TAG = CountryDao.class.getSimpleName();

    private SQLiteDatabase db = null;

    String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ").append(DATABASE_TABLE)
            .toString();

    public CountryDao() {

        dbHelper = DbHelper.getInstance();

    }

    public void insertIntoCountries(List<Country> countries) {

        synchronized (DbHelper.lock) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                Log.i(TAG, "going to insert into countries :");
                db.beginTransaction();

                for (Country country : countries) {

                    ContentValues values = new ContentValues();
                    values.put(COUNTRY_NAME, country.getName());
                    values.put(COUNTRY_RANK, country.getRank());
                    values.put(COUNTRY_POINT, country.getPoints()); // user
                    values.put(COUNTRY_URL, country.getCountryLink()); 
                    values.put(Constants.UPDATED_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    // session
                    // Inserting Row
                    db.insert(DATABASE_TABLE, null, values);

                }

                db.setTransactionSuccessful();
                Log.i(TAG, "successfully inserted into country :");

            } catch (SQLException e) {
                Log.e(TAG, "SQLException :", e);

            } finally {
                db.endTransaction();
                db.close();
            }
        }

    }

    public List<Country> getAllCountries() {

        List<Country> countryList = new ArrayList<Country>();

        synchronized (DbHelper.lock) {

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(new StringBuffer(SELECT_ALL_ROWS).toString(), null);

            try {

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Country country = new Country();

                        country.set_id(cursor.getString(cursor.getColumnIndex(COUNTRY_ID)));
                        country.setRank(cursor.getString(cursor.getColumnIndex(COUNTRY_RANK)));
                        country.setName(cursor.getString(cursor.getColumnIndex(COUNTRY_NAME)));
                        country.setPoints(cursor.getString(cursor.getColumnIndex(COUNTRY_POINT)));
                        country.setCountryLink(cursor.getString(cursor.getColumnIndex(COUNTRY_URL)));
                        country.setUpdatedDate(cursor.getString(cursor.getColumnIndex(Constants.UPDATED_DATE)));
                        countryList.add(country);

                    }

                }

            } catch (Exception e) {
                Log.e(TAG, "Exception while database operation", e);

            } finally {

                cursor.close();
                db.close();

            }

            return countryList;

        }

    }

}
