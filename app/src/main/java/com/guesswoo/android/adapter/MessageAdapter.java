package com.guesswoo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guesswoo.android.R;
import com.guesswoo.android.domain.Game;
import com.guesswoo.android.domain.Message;

import java.io.FileNotFoundException;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    private Context context;
    private int layoutResourceId;
    private final List<Message> messages;
    private final Game game;

    public MessageAdapter(Context context, int layoutResourceId, List<Message> messages, Game game) {
        super(context, layoutResourceId, messages);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.messages = messages;
        this.game = game;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        MessageHolder messageHolder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            messageHolder = new MessageHolder();
            messageHolder.ivProfileLeft = (ImageView) row.findViewById(R.id.ivProfileLeft);
            messageHolder.ivProfileRight = (ImageView) row.findViewById(R.id.ivProfileRight);
            messageHolder.llContent = (LinearLayout) row.findViewById(R.id.llContent);
            messageHolder.llBackground = (LinearLayout) row.findViewById(R.id.llBackground);
            messageHolder.tvBody = (TextView) row.findViewById(R.id.tvBody);
            messageHolder.tvDateTime = (TextView) row.findViewById(R.id.tvDateTime);

            row.setTag(messageHolder);
        } else {
            messageHolder = (MessageHolder) row.getTag();
        }

        Message message = messages.get(position);

        if (message.isMe()) {

            messageHolder.llBackground.setBackgroundResource(R.drawable.abc_dialog_material_background_light);
            messageHolder.tvBody.setTextColor(Color.BLACK);

            messageHolder.ivProfileLeft.setVisibility(View.INVISIBLE);
            messageHolder.ivProfileRight.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) messageHolder.llBackground.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            messageHolder.llBackground.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) messageHolder.llContent.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            messageHolder.llContent.setLayoutParams(lp);

            layoutParams = (LinearLayout.LayoutParams) messageHolder.tvBody.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            messageHolder.tvBody.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) messageHolder.tvDateTime.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            messageHolder.tvDateTime.setLayoutParams(layoutParams);

            try {
                Bitmap photo = BitmapFactory.decodeStream(getContext().openFileInput(game.getInitialPhoto()));
                RoundedBitmapDrawable circularPhoto =
                        RoundedBitmapDrawableFactory.create(getContext().getResources(), photo);
                circularPhoto.setCornerRadius(photo.getWidth());

                messageHolder.ivProfileRight.setImageDrawable(circularPhoto);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {

            messageHolder.llBackground.setBackgroundResource(R.drawable.abc_dialog_material_background_dark);
            messageHolder.tvBody.setTextColor(Color.WHITE);

            messageHolder.ivProfileLeft.setVisibility(View.VISIBLE);
            messageHolder.ivProfileRight.setVisibility(View.INVISIBLE);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) messageHolder.llBackground.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            messageHolder.llBackground.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) messageHolder.llContent.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            messageHolder.llContent.setLayoutParams(lp);

            layoutParams = (LinearLayout.LayoutParams) messageHolder.tvBody.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            messageHolder.tvBody.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) messageHolder.tvDateTime.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            messageHolder.tvDateTime.setLayoutParams(layoutParams);

            try {
                Bitmap photo = BitmapFactory.decodeStream(getContext().openFileInput(game.getBoard().get(0).getPhoto
                        ()));
                RoundedBitmapDrawable circularPhoto =
                        RoundedBitmapDrawableFactory.create(getContext().getResources(), photo);
                circularPhoto.setCornerRadius(photo.getWidth());

                messageHolder.ivProfileLeft.setImageDrawable(circularPhoto);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        messageHolder.tvBody.setText(message.getBody());
        messageHolder.tvDateTime.setText(message.getDateTime());

        return row;
    }

    static class MessageHolder {
        ImageView ivProfileLeft;
        ImageView ivProfileRight;
        LinearLayout llContent;
        LinearLayout llBackground;
        TextView tvBody;
        TextView tvDateTime;
    }
}
