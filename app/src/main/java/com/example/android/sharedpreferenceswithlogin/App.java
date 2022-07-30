package com.example.android.sharedpreferenceswithlogin;

import android.app.Application;
import android.content.SharedPreferences;

public class App extends Application {
    public static final String LOGIN_STATUS_KEY = "LOGIN_CHECK_KEY";
    public static SharedPreferences mySharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        mySharedPreferences = getSharedPreferences(getApplicationInfo().packageName+"_shared_Pref_file",MODE_PRIVATE);
    }
}
