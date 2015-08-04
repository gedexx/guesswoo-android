package com.guesswoo.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;


/**
 * Ecran permettant de s'authentifier avec un login et un password
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    // Couples login/password bouchonnés
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "zdededexx@mail.com:password", "test@mail.com:password"
    };

    private UserLoginTask mAuthTask = null;

    @ViewById(R.id.tvLblGuessWoo)
    protected TextView tvLblGuessWoo;

    @ViewById(R.id.email)
    protected AutoCompleteTextView mEmailView;

    @ViewById(R.id.password)
    protected EditText mPasswordView;

    @ViewById(R.id.email_sign_in_button)
    protected Button mEmailSignInButton;

    @ViewById(R.id.login_progress)
    protected View mProgressView;

    @ViewById(R.id.login_form)
    protected View mLoginFormView;

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

    @Click(R.id.email_sign_in_button)
    public void onClickSignInButton() {
        attemptLogin();
    }


    /**
     * Lance une tentative de login à partir des éléments saisis dans le formulaire
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Effaçage d'éventuelles anciennes traces d'erreurs sur le formulaire
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Récupération des saisies faites dans le formulaire de login
        String email = mEmailView.getText().toString();
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
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // Un des champs est mal valorisé, le champ concerné est focus et affiche le message d'erreur
            focusView.requestFocus();
        } else {
            // Déclenche l'animation de la barre de progression puis la tâche asynchrone symbolisant la tentative de
            // login d'un utilisateur (à remplacer par un Service + BroadcastReceiver ?)
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Affiche la barre de progression tout en cachant le formulaire (à cause du thème AppCompat, l'affichage est
     * dégueu avec un Spinner au lieu d'une barre bleue au niveau de l'action bar comme dans Lollipop...)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

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

    /**
     * Tâche asynchrone de login (utiliser plutôt un Service et un BroadcastReceiver ?)
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Pour simuler l'attente d'un login réel
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Permet de vérifier la bonne connexion (à partir du bouchon mis en place)
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO : Gérer la création du compte ?
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                startActivity(new Intent(getApplicationContext(), MainActivity_.class));
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

