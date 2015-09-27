package com.guesswoo.android.service.rest;

import android.app.IntentService;
import android.content.Intent;

import com.guesswoo.android.GuessWooApplication;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EIntentService;

@EIntentService
public class LoginService extends IntentService {

    @App
    GuessWooApplication guessWooApplication;

    public LoginService() {
        super("LoginService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }
}
