package com.guesswoo.android.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@DatabaseTable(tableName = "games")
public class Game implements Serializable {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String username;

    @DatabaseField
    private Boolean author;

    @DatabaseField
    private Boolean connected;

    @DatabaseField
    private Long unreadMessagesCount;

    @DatabaseField
    private Date date;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Photo> board;

    @DatabaseField
    private Integer activePhotos;

    @DatabaseField
    private String initialPhoto;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LastMessage lastMessage;

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthor() {
        return author;
    }

    public void setAuthor(Boolean author) {
        this.author = author;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Long getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Long unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Photo> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<Photo> board) {
        this.board = board;
    }

    public Integer getActivePhotos() {
        return activePhotos;
    }

    public void setActivePhotos(Integer activePhotos) {
        this.activePhotos = activePhotos;
    }

    public String getInitialPhoto() {
        return initialPhoto;
    }

    public void setInitialPhoto(String initialPhoto) {
        this.initialPhoto = initialPhoto;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static class Photo implements Serializable {

        private String photo;
        private Boolean excluded;

        public Photo() {
        }

        public String getPhoto() {
            return this.photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Boolean getExcluded() {
            return this.excluded;
        }

        public void setExcluded(Boolean excluded) {
            this.excluded = excluded;
        }
    }

    public static class LastMessage implements Serializable {
        private String username;
        private Object content;
        private Date date;

        public LastMessage() {
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getContent() {
            return this.content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public Date getDate() {
            return this.date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
