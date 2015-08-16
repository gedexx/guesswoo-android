package com.guesswoo.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.ListView;

import com.guesswoo.android.R;
import com.guesswoo.android.activity.GameActivity_;
import com.guesswoo.android.adapter.GameAdapter;
import com.guesswoo.android.domain.Game;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String USERNAME = "username";
    @ViewById(R.id.lvGames)
    protected ListView lvGames;

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

        GameAdapter gameAdapter = new GameAdapter(getActivity(), R.layout.listview_game_row, dummyGames());

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

        startActivity(gameIntent);
    }

    private List<Game> dummyGames() {
        List<Game> games = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            games.add(new Game("User " + i, new Date(), null, random.nextInt(20)));
        }

        return games;
    }
}
