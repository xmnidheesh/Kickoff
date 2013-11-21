
package com.jn.kickoff.manager;

import android.content.Context;

import com.jn.kickoff.db.DbHelper;

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
