package com.guesswoo.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.guesswoo.android.GuessWooApplication;
import com.guesswoo.android.R;
import com.guesswoo.android.activity.GameActivity_;
import com.guesswoo.android.adapter.GameAdapter;
import com.guesswoo.android.domain.Game;
import com.guesswoo.android.domain.Message;
import com.guesswoo.android.helper.database.GuessWooDatabaseHelper;
import com.guesswoo.api.dto.responses.MessageResponse;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.NestedRuntimeException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String USERNAME = "username";

    @App
    GuessWooApplication application;

    @ViewById(R.id.lvGames)
    protected ListView lvGames;

    @OrmLiteDao(helper = GuessWooDatabaseHelper.class)
    Dao<Game, String> gameDao;

    @OrmLiteDao(helper = GuessWooDatabaseHelper.class)
    Dao<Message, String> messageDao;

    /**
     * Instance du fragment, pour gérer les changements d'orientation de l'activity (permet, à la recréation de
     * l'activity qui expose le fragment, de récupérer toutes les valeurs avant basculement de l'affichage)
     *
     * @return
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainFragment() {
    }

    @AfterViews
    protected void init() {

        GameAdapter gameAdapter = null;
        try {
            QueryBuilder<Game, String> qb = gameDao.queryBuilder();
            qb.where().like("id", "%" + application.getConnectedUsername() + "%");
            PreparedQuery<Game> pq = qb.prepare();
            gameAdapter = new GameAdapter(getActivity(), R.layout.listview_game_row, gameDao.query(pq));
        } catch (SQLException e) {
            Log.e(MainFragment.class.getName(), "Can't retrieve games data", e);
            throw new RuntimeException(e);
        }

        // Set the adapter
        lvGames.setAdapter(gameAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @ItemClick(R.id.lvGames)
    public void onItemClick(int position) {

        Game selectedGame = (Game) lvGames.getItemAtPosition(position);

        Intent gameIntent = new Intent(getActivity(), GameActivity_.class);
        gameIntent.putExtra(USERNAME, selectedGame.getUsername());

        doGetMessagesFromGameInBackground(selectedGame.getUsername());

        startActivity(gameIntent);
    }

    @Background
    protected void doGetMessagesFromGameInBackground(String username) {
        try {
            for (MessageResponse messageResponse : application.getGameService().getMessagesFromGame(username)) {

                Message message = new Message(messageResponse.getId(), messageResponse.getGameId(), messageResponse
                        .getContent().toString(), DateFormat
                        .getDateTimeInstance().format(messageResponse.getDate()), messageResponse.getFrom().equals
                        (application.getConnectedUsername()));

                try {
                    messageDao.createOrUpdate(message);
                } catch (SQLException e) {
                    Log.e(GuessWooDatabaseHelper.class.getName(), "Can't insert retrieved messages", e);
                    throw new RuntimeException(e);
                }
            }

        } catch (NestedRuntimeException e) {

        }
    }
}
