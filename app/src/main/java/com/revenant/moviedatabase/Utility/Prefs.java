package com.revenant.moviedatabase.Utility;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Or on 10-Jan-18.
 */

public class Prefs {
    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences= activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void setSearch(String search){
        sharedPreferences.edit().putString("search", search).commit();
    }

    public String getSearch(){
        return sharedPreferences.getString("search", "Batman");
    }
}
