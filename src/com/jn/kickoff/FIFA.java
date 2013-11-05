package com.jn.kickoff;

import android.app.Application;
import android.content.Context;

public class FIFA  extends Application{
    
    private static Context context ;

    /**
     * @return the context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public static void setContext(Context context) {
        FIFA.context = context;
    }
    
    

}
