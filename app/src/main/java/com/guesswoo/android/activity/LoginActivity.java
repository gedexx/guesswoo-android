package com.guesswoo.android.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guesswoo.android.GuessWooApplication;
import com.guesswoo.android.R;
import com.guesswoo.android.service.rest.UserService;
import com.guesswoo.android.service.rest.response.LoginResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.core.NestedRuntimeException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * Ecran permettant de s'authentifier avec un login et un password
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @App
    GuessWooApplication application;

    @ViewById(R.id.tvLblGuessWoo)
    protected TextView tvLblGuessWoo;

    @ViewById(R.id.user)
    protected AutoCompleteTextView mUserView;

    @ViewById(R.id.password)
    protected EditText mPasswordView;

    @ViewById(R.id.login_button)
    protected Button mLoginSignInButton;

    @ViewById(R.id.login_progress)
    protected View mProgressView;

    @ViewById(R.id.login_form)
    protected View mLoginFormView;

    @RestService
    UserService userService;

    @AfterViews
    protected void init() {
        // Action d'initialisation après création de la vue (via les annotations @EActivity pour l'activité et
        // @ViewById pour les composants présents sur le layout de l'activité)
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        tvLblGuessWoo.setTypeface(typeFace);
    }

    @EditorAction(R.id.password)
    public boolean onEditPassword(TextView password, int actionId, KeyEvent keyEvent) {
        if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    @Click(R.id.login_button)
    public void onClickLoginButton() {
        attemptLogin();
    }


    /**
     * Lance une tentative de login à partir des éléments saisis dans le formulaire
     */
    public void attemptLogin() {

        // Effaçage d'éventuelles anciennes traces d'erreurs sur le formulaire
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Récupération des saisies faites dans le formulaire de login
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Vérifie la validité du password, si saisi
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Vérifie la validité du mail saisi
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // Un des champs est mal valorisé, le champ concerné est focus et affiche le message d'erreur
            focusView.requestFocus();
        } else {
            // Déclenche l'animation de la barre de progression puis la tâche asynchrone symbolisant la tentative de
            // login d'un utilisateur (à remplacer par un Service + BroadcastReceiver ?)
            showProgress(true);
            doLoginInBackground(username, password);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    /**
     * Affiche la barre de progression tout en cachant le formulaire (à cause du thème AppCompat, l'affichage est
     * dégueu avec un Spinner au lieu d'une barre bleue au niveau de l'action bar comme dans Lollipop...)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @UiThread
    protected void showProgress(final boolean show) {

        // Compatibilité de version Android, tout ça...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @UiThread
    protected void showError() {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Background
    protected void doLoginInBackground(String username, String password) {

        try {
            // Construction des paramètres à passer au login
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.set("username", username);
            formData.set("password", password);

            application.setLoginResponse(userService.login(formData));
        } catch (NestedRuntimeException e) {
            application.setLoginResponse(new LoginResponse());
        }

        boolean success = !TextUtils.isEmpty(application.getLoginResponse()
                .getToken());

        if (success) {
            startActivity(new Intent(getApplicationContext(), MainActivity_.class));
        } else {
            showError();
        }

        showProgress(false);
    }

}

