package com.guesswoo.android.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.guesswoo.android.GuessWooApplication;
import com.guesswoo.android.R;
import com.guesswoo.android.domain.Game;
import com.guesswoo.android.helper.database.GuessWooDatabaseHelper;
import com.guesswoo.api.dto.responses.GameResponse;
import com.guesswoo.api.dto.responses.TokenResponse;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.NestedRuntimeException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Ecran permettant de s'authentifier avec un login et un password
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    @ViewById(R.id.tvLblGuessWoo)
    protected TextView tvLblGuessWoo;
    @ViewById(R.id.user)
    protected AutoCompleteTextView mUserView;
    @ViewById(R.id.password)
    protected EditText mPasswordView;
    @ViewById(R.id.login_progress)
    protected View mProgressView;
    @ViewById(R.id.login_form)
    protected View mLoginFormView;
    protected GuessWooDatabaseHelper guessWooDatabaseHelper;
    @OrmLiteDao(helper = GuessWooDatabaseHelper.class)
    protected Dao<Game, String> gameDao;
    @App
    GuessWooApplication application;

    @AfterViews
    protected void init() {
        // Action d'initialisation après création de la vue (via les annotations @EActivity pour l'activité et
        // @ViewById pour les composants présents sur le layout de l'activité)
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        tvLblGuessWoo.setTypeface(typeFace);

        guessWooDatabaseHelper = new GuessWooDatabaseHelper(getApplicationContext());
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

        TokenResponse tokenResponse;
        try {
            // Construction des paramètres à passer au login
            final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.set(USERNAME, username);
            formData.set(PASSWORD, password);

            tokenResponse = (application.getUserService().login(formData));
        } catch (NestedRuntimeException e) {
            tokenResponse = new TokenResponse();
        }

        boolean success = !TextUtils.isEmpty(tokenResponse.getToken());

        if (success) {

            guessWooDatabaseHelper.emptyTables();

            List<Game> games = new ArrayList<>();
            try {

                application.getGameService().setHeader(GuessWooApplication.X_TOKEN, tokenResponse.getToken());
                for (GameResponse gameResponse : application.getGameService().getGames()) {

                    final Game game = new Game();
                    game.setId(gameResponse.getId());
                    game.setUsername(gameResponse.getUsername());
                    game.setDate(gameResponse.getDate());
                    game.setInitialPhoto(gameResponse.getInitialPhoto());

                    try {
                        final String initialPhotoNameFile = game.getInitialPhoto();
                        final FileOutputStream fos = openFileOutput(initialPhotoNameFile, Context.MODE_PRIVATE);

                        // Use the compress method on the BitMap object to write image to the OutputStream
                        final Bitmap bitmapImage = BitmapFactory.decodeStream(application.getPhotoService().getThumbnail
                                (initialPhotoNameFile).getInputStream());
                        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    game.setActivePhotos(gameResponse.getActivePhotos());
                    game.setAuthor(gameResponse.getAuthor());
                    game.setConnected(gameResponse.getConnected());
                    game.setLastMessage(constructLastMessage(gameResponse.getLastMessage()));
                    game.setUnreadMessagesCount(gameResponse.getUnreadMessagesCount());
                    game.setBoard(constructBoard(gameResponse.getBoard()));

                    games.add(game);
                }
            } catch (NestedRuntimeException e) {
                games = new ArrayList<>();
            }

            try {
                for (Game game : games) {
                    gameDao.createOrUpdate(game);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            application.setConnectedUsername(username);
            startActivity(new Intent(getApplicationContext(), MainActivity_.class));
        } else {
            showError();
        }

        showProgress(false);
    }

    private Game.LastMessage constructLastMessage(GameResponse.LastMessage gameResponseLastMessage) {

        final Game.LastMessage lastMessage = new Game.LastMessage();

        if (gameResponseLastMessage != null) {
            lastMessage.setUsername(gameResponseLastMessage.getUsername());
            lastMessage.setDate(gameResponseLastMessage.getDate());
            lastMessage.setContent(gameResponseLastMessage.getContent());
        }

        return lastMessage;
    }

    private ArrayList<Game.Photo> constructBoard(List<GameResponse.Photo> gameResponseBoard) {

        final ArrayList<Game.Photo> board = new ArrayList<>();

        if (!CollectionUtils.isEmpty(gameResponseBoard)) {

            for (GameResponse.Photo gameResponsePhoto : gameResponseBoard) {

                final Game.Photo photo = new Game.Photo();
                photo.setPhoto(gameResponsePhoto.getPhoto());
                photo.setExcluded(gameResponsePhoto.getExcluded());

                try {
                    final String photoNameFile = photo.getPhoto();

                    final FileOutputStream fos = openFileOutput(photoNameFile, Context.MODE_PRIVATE);

                    // Use the compress method on the BitMap object to write image to the OutputStream
                    final Bitmap bitmapImage = BitmapFactory.decodeStream(application.getPhotoService().getThumbnail
                            (photoNameFile).getInputStream());
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                board.add(photo);
            }
        }

        return board;
    }

}

