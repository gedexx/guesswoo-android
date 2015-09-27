package com.guesswoo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guesswoo.android.R;
import com.guesswoo.android.adapter.utils.AdapterUtils;
import com.guesswoo.android.domain.Game;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Game> {

    private Context context;
    private int layoutResourceId;
    private final List<Game> games;


    public GameAdapter(Context context, int layoutResourceId, List<Game> games) {
        super(context, layoutResourceId, games);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.games = games;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        GameHolder gameHolder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            gameHolder = new GameHolder();
            gameHolder.ivUserPhoto = (ImageView) row.findViewById(R.id.ivUserPhoto);
            gameHolder.tvUserName = (TextView) row.findViewById(R.id.tvUserName);
            gameHolder.tvUpdatedDate = (TextView) row.findViewById(R.id.tvUpdatedDate);
            gameHolder.tvPhotosToDiscoverNb = (TextView) row.findViewById(R.id.tvPhotosToDiscoverNb);

            row.setTag(gameHolder);
        } else {
            gameHolder = (GameHolder) row.getTag();
        }

        Game game = games.get(position);
        gameHolder.ivUserPhoto.setImageResource(R.mipmap.ic_launcher);
        gameHolder.tvUserName.setText(game.getUsername());
        gameHolder.tvUpdatedDate.setText(AdapterUtils.getFormattedDate(game.getUpdatedDate()));
        gameHolder.tvPhotosToDiscoverNb.setText(game.getPhotosToDiscoverNb() + "photos restante(s)");

        return row;
    }

    static class GameHolder {
        ImageView ivUserPhoto;
        TextView tvUserName;
        TextView tvUpdatedDate;
        TextView tvPhotosToDiscoverNb;
    }

}
