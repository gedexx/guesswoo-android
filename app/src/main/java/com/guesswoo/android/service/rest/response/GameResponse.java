package com.guesswoo.android.service.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class GameResponse {

    private String id;
    private String username;
    private boolean author;
    private boolean connected;
    private int unreadMessagesCount;
    private Date date;
    private List<BoardResponse> board;
    private int activePhotos;
    private String initialPhoto;
    private MessageResponse lastMessage;
    private boolean closed;

    @JsonIgnore
    private ProfilResponse opponentProfil;

    public int getActivePhotos() {
        return activePhotos;
    }

    public void setActivePhotos(int activePhotos) {
        this.activePhotos = activePhotos;
    }

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public List<BoardResponse> getBoard() {
        return board;
    }

    public void setBoard(List<BoardResponse> board) {
        this.board = board;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitialPhoto() {
        return initialPhoto;
    }

    public void setInitialPhoto(String initialPhoto) {
        this.initialPhoto = initialPhoto;
    }

    public MessageResponse getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageResponse lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(int unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ProfilResponse getOpponentProfil() {
        return opponentProfil;
    }

    public void setOpponentProfil(ProfilResponse opponentProfil) {
        this.opponentProfil = opponentProfil;
    }
}
