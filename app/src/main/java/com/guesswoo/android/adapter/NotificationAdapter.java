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
import com.guesswoo.android.domain.Notification;

import java.util.List;
import java.util.Random;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    private Context context;
    private int layoutResourceId;
    private final List<Notification> notifications;


    public NotificationAdapter(Context context, int layoutResourceId, List<Notification> notifications) {
        super(context, layoutResourceId, notifications);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.notifications = notifications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        NotificationHolder notificationHolder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            notificationHolder = new NotificationHolder();
            notificationHolder.ivNotificationIcon = (ImageView) row.findViewById(R.id.ivNotificationIcon);
            notificationHolder.tvNotificationType = (TextView) row.findViewById(R.id.tvNotificationType);
            notificationHolder.tvNotificationUpdatedDate = (TextView) row.findViewById(R.id.tvNotificationUpdatedDate);

            row.setTag(notificationHolder);
        } else {
            notificationHolder = (NotificationHolder) row.getTag();
        }

        Notification notification = notifications.get(position);
        notificationHolder.ivNotificationIcon.setImageResource(R.mipmap.ic_launcher);
        notificationHolder.tvNotificationType.setText(notification.getType());
        notificationHolder.tvNotificationUpdatedDate.setText(getFormattedDate());

        return row;
    }

    /**
     * Bouchon
     *
     * @return
     */
    private CharSequence getFormattedDate() {

        Random random = new Random();

        int nbMinutes = random.nextInt(10080);

        if (nbMinutes < 60) {
            return "il y a " + nbMinutes + " minute(s)";
        } else if (nbMinutes < 1440) {
            return "il y a " + nbMinutes / 60 + " heure(s)";
        } else if (nbMinutes < 10080) {
            return "il y a " + nbMinutes / 60 / 24 + " jour(s)";
        }

        return null;
    }

    static class NotificationHolder {
        ImageView ivNotificationIcon;
        TextView tvNotificationType;
        TextView tvNotificationUpdatedDate;
    }
}
