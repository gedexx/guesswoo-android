package com.guesswoo.android.helper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.guesswoo.android.domain.Game;
import com.guesswoo.android.domain.Message;
import com.guesswoo.android.domain.Notification;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class GuessWooDatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "guesswoo.db";
    private static final int DATABASE_VERSION = 1;

    public GuessWooDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            Log.i(GuessWooDatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Game.class);
            TableUtils.createTable(connectionSource, Message.class);
            TableUtils.createTable(connectionSource, Notification.class);
        } catch (SQLException e) {
            Log.e(GuessWooDatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // Insertion initial en SQLite
        insertDummyGames();
        insertDummyMessages();
        insertDummyNotifications();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            Log.i(GuessWooDatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Game.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(GuessWooDatabaseHelper.class.getName(), "Can't drop database", e);
            throw new RuntimeException(e);
        }
    }

    private void insertDummyGames() {

        try {
            for (int i = 0; i < 20; i++) {
                Random random = new Random();
                getDao(Game.class).createIfNotExists(new Game((long) i, "User " + i, getRandomDate(), null,
                        random.nextInt
                                (20)));
            }
        } catch (SQLException e) {
            Log.e(GuessWooDatabaseHelper.class.getName(), "Can't insert games", e);
            throw new RuntimeException(e);
        }
    }

    private void insertDummyMessages() {

        try {
            for (int i = 0; i < 15; i++) {
                getDao(Message.class).createIfNotExists(new Message((long) i, "User 1", "Message " + i, DateFormat
                        .getDateTimeInstance().format
                                (getRandomDate()), false));
            }
        } catch (SQLException e) {
            Log.e(GuessWooDatabaseHelper.class.getName(), "Can't insert messages", e);
            throw new RuntimeException(e);
        }
    }

    private void insertDummyNotifications() {

        try {
            for (int i = 0; i < 15; i++) {
                getDao(Notification.class).createIfNotExists(new Notification((long) i, "Notification " + i,
                        getRandomDate(), null));
            }
        } catch (SQLException e) {
            Log.e(GuessWooDatabaseHelper.class.getName(), "Can't insert notifications", e);
            throw new RuntimeException(e);
        }
    }

    private Timestamp getRandomDate() {

        Calendar today = Calendar.getInstance();
        Calendar oneMonthLater = Calendar.getInstance();
        oneMonthLater.add(Calendar.MONTH, -1);

        long end = today.getTime().getTime();
        long offset = oneMonthLater.getTime().getTime();
        long diff = end - offset + 1;

        return new Timestamp(offset + (long) (Math.random() * diff));
    }
}
