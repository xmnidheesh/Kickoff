
package com.jn.kickoff.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jn.kickoff.FIFA;
import com.jn.kickoff.constants.Constants;

public class DbHelper extends SQLiteOpenHelper implements Constants {

    private static final String TAG = DbHelper.class.getName();

    private Context myContext = FIFA.getContext();

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.jn.kickoff/databases/";

    private static String DB_NAME = "fifa_14.db";

    private SQLiteDatabase myDataBase;

    private static DbHelper mInstance = null;

    public static final Object lock = new Object();

    private static final int DB_VERSION_NO = 1;

    public static DbHelper getInstance() {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DbHelper(FIFA.getContext());
        }
        return mInstance;
    }

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     * 
     * @param context
     */
    public DbHelper(Context context) {
        super(FIFA.getContext(), DB_NAME, null, DB_VERSION_NO);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            SQLiteDatabase db = this.getWritableDatabase();

            try {

                copyDataBase();

                createNewTables(db);

            } catch (IOException e) {

                throw new Error("Error copying database");

            } finally {

                if (db != null)
                    db.close();
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     * 
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        boolean checkdb = false;
        try {
            String myPath = myContext.getFilesDir().getAbsolutePath().replace("files", "databases")
                    + File.separator + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase;
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    void createNewTables(SQLiteDatabase db) {

        db.execSQL(new StringBuilder(" CREATE TABLE ").append(Country.DATABASE_TABLE).append(" (")
                .append(Country.COUNTRY_ID).append(" INTEGER PRIMARY KEY ,")
                .append(Country.COUNTRY_NAME).append(" Varchar(20),").append(Country.COUNTRY_RANK)
                .append(" Varchar(20),").append(Country.COUNTRY_POINT).append(" Varchar(20),")
                .append(Country.COUNTRY_URL).append(" Varchar(20),").append(UPDATED_DATE)
                .append(" timestamp").append(");").toString());

        db.execSQL(new StringBuilder(" CREATE TABLE ").append(Players.DATABASE_TABLE).append(" (")
                .append(Players.PLAYER_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT ,")
                .append(Players.COUNTRY_ID).append(" INTEGER,").append(Players.PLAYER_NAME)
                .append(" Varchar(20),").append(Players.PLAYER_IMAGE_LINK)
                .append(" Varchar(20) NOT NULL,").append(Players.PLAYER_PROFILE_LINK)
                .append(" Varchar(20) NOT NULL,").append(UPDATED_DATE)
                .append(" timestamp,").append(" FOREIGN KEY ").append("(")
                .append(Players.COUNTRY_ID).append(")").append(" REFERENCES ")
                .append(Country.DATABASE_TABLE).append(" ( ").append(Country.COUNTRY_ID)
                .append(" )").append(");").toString());

        db.execSQL(new StringBuilder(" CREATE TABLE ").append(PlayerProfile.DATABASE_TABLE)
                .append(" (").append(PlayerProfile._ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT ,").append(PlayerProfile.PLAYER_ID)
                .append(" INTEGER,").append(PlayerProfile.COUNTRY_ID).append(" INTEGER,")

                .append(PlayerProfile.PLAYER_FNAME).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_LNAME).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_IMAGE_LINK).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_NATIONALITY).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_DOB).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_AGE).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_COUNTRY).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_PLACE).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_POSTION).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_HEIGHT).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_WEIGHT).append(" Varchar(20),")
                .append(PlayerProfile.PLAYER_FOOT).append(" Varchar(20),")
                .append(UPDATED_DATE).append(" timestamp,")
                
                .append(" FOREIGN KEY ").append("(").append(PlayerProfile.PLAYER_ID).append(")")
                .append(" REFERENCES ").append(Players.DATABASE_TABLE).append(" ( ")
                .append(Players.PLAYER_ID).append(" ),")

                .append(" FOREIGN KEY ").append("(").append(PlayerProfile.COUNTRY_ID).append(")")
                .append(" REFERENCES ").append(Country.DATABASE_TABLE).append(" ( ")
                .append(Country.COUNTRY_ID).append(" )")

                .append(");").toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(Country.DATABASE_TABLE)
                .toString());

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(Players.DATABASE_TABLE)
                .toString());

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(PlayerProfile.DATABASE_TABLE)
                .toString());

        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

}
