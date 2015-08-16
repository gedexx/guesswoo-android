package com.guesswoo.android.domain;

public class Message {

    private String userId;
    private String body;
    private String dateTime;
    private boolean isMe;

    public Message() {
    }

    public Message(String userId, String body, String dateTime, boolean isMe) {
        this.userId = userId;
        this.body = body;
        this.dateTime = dateTime;
        this.isMe = isMe;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
