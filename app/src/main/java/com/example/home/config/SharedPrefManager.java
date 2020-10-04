package com.example.home.config;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager
{

    //constant name to store the userId and the sharedPref name
    private static final String PREF_NAME = "com.example.home";
    private static final String USER_ID = "com.example.home";

    public static void setLoginUserId(Context context,long id)
    {
        //set the currently logged user id in SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //put the id
        editor.putLong(USER_ID,id);
        //apply the changes
        editor.apply();
    }
    public static long getLoginUserId(Context context)
    {
        //get the user id from SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        //return the id
        //if there is no valid id
        //just return -99
        //to represent there is no valid user id
        return preferences.getLong(USER_ID,-99);
    }
}
