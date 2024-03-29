package com.guesswoo.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "messages")
public class Message {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String gameId;

    @DatabaseField
    private String body;

    @DatabaseField
    private String dateTime;

    @DatabaseField
    private boolean isMe;

    public Message() {
    }

    public Message(String id, String gameId, String body, String dateTime, boolean isMe) {
        this.id = id;
        this.gameId = gameId;
        this.body = body;
        this.dateTime = dateTime;
        this.isMe = isMe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }
}
