package com.guesswoo.android;

import android.app.Application;
import android.app.NotificationManager;

import com.guesswoo.android.service.rest.GameService;
import com.guesswoo.android.service.rest.PhotoService;
import com.guesswoo.android.service.rest.UserService;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.rest.RestService;

@EApplication
public class GuessWooApplication extends Application {

    public static final String X_TOKEN = "X-Token";

    private String connectedUsername;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SystemService
    NotificationManager notificationManager;

    @RestService
    GameService gameService;

    @RestService
    UserService userService;

    @RestService
    PhotoService photoService;

    public String getConnectedUsername() {
        return connectedUsername;
    }

    public void setConnectedUsername(String connectedUsername) {
        this.connectedUsername = connectedUsername;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public GameService getGameService() {
        return gameService;
    }

    public UserService getUserService() {
        return userService;
    }

    public PhotoService getPhotoService() {
        return photoService;
    }
}
