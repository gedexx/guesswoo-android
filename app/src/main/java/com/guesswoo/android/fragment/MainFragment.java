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
import com.guesswoo.android.service.rest.response.MessageResponseTemp;
import com.guesswoo.api.dto.enums.MessageType;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.NestedRuntimeException;

import java.sql.SQLException;
import java.text.DateFormat;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String GAME = "game";

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
            gameAdapter = new GameAdapter(getActivity(), R.layout.listview_game_row, gameDao.queryForAll());
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

    @UiThread
    @ItemClick(R.id.lvGames)
    public void onItemClick(int position) {

        Game selectedGame = (Game) lvGames.getItemAtPosition(position);
        doGetMessagesFromGameInBackground(selectedGame.getUsername());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent gameIntent = new Intent(getActivity(), GameActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GAME, selectedGame);
        gameIntent.putExtras(bundle);

        startActivity(gameIntent);
    }

    @Background
    protected void doGetMessagesFromGameInBackground(String username) {
        try {
            for (MessageResponseTemp messageResponse : application.getGameService().getMessagesFromGame(username)) {

                if (MessageType.CHAT.equals(messageResponse.getType())) {

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
            }

        } catch (NestedRuntimeException e) {

        }
    }
}
