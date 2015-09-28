package com.guesswoo.android;

import android.app.Application;
import android.app.NotificationManager;

import com.guesswoo.android.service.rest.response.LoginResponse;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

@EApplication
public class GuessWooApplication extends Application {

    private LoginResponse loginResponse;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SystemService
    NotificationManager notificationManager;

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
