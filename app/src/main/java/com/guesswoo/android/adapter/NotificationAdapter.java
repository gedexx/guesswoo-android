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
import com.guesswoo.android.domain.Notification;

import java.util.List;

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
        notificationHolder.tvNotificationUpdatedDate.setText(AdapterUtils.getFormattedDate(notification
                .getUpdatedDate()));

        return row;
    }

    static class NotificationHolder {
        ImageView ivNotificationIcon;
        TextView tvNotificationType;
        TextView tvNotificationUpdatedDate;
    }
}
