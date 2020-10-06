package com.example.pet;

import android.app.Application;
import android.content.SharedPreferences;

public class App extends Application
{
   private CommunicationService communicationService;
   private SharedPreferences sharedPreferences;
   public static String PREFS_NAME = "my_preferences";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        communicationService = new CommunicationServiceImpl();
    }

    public SharedPreferences getPrefs()
    {
        return sharedPreferences;
    }

    public CommunicationService getCommunicationService() { return communicationService; }
}
