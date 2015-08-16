package com.guesswoo.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.guesswoo.android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Ecran splash pour le démarrage de l'application (sensé camoufler les différents traitements d'initialisation de
 * l'application)
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    /**
     * Durée de l'écran de démarrage avant apparation de l'activité de login
     */
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @AfterViews
    protected void init() {

        /* Handler pour afficher l'activité de login après un nombre de secondes défini.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Crée une Intent qui démarrera l'activité de login. */
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity_.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}