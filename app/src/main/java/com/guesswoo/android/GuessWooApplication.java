package com.guesswoo.android;

import android.app.Application;
import android.app.NotificationManager;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

@EApplication
public class GuessWooApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SystemService
    NotificationManager notificationManager;

}
