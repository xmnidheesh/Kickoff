
package com.jn.kickoff.manager;

import com.jn.kickoff.db.DbHelper;

import android.content.Context;

public class Manager {

    DbHelper db;

    Context context;

    public Manager(Context context) {

        db = DbHelper.getInstance();

        this.context = context;
    }

    public void start() {

        db.getWritableDatabase();

        db.close();

    }

    public void reCreateDb() {

    }

}
