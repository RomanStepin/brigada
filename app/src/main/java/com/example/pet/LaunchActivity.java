package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.analytics.FirebaseAnalytics;

public class LaunchActivity extends AppCompatActivity {

    SharedPreferences preferences;
    public static int LAUNCH_TIME = 300;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        preferences = ((App)getApplication()).getPrefs();
        new Handler(Looper.myLooper()).postDelayed(()->{
            if (preferences.getBoolean("isNamelessLaunch", true)) {
                LoginActivity.startInNewTask(this);
            }
        }, LAUNCH_TIME);
    }
}

