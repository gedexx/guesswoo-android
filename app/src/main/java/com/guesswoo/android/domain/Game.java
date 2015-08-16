package com.guesswoo.android.domain;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class Game {

    private String username;
    private Date updatedDate;
    private Drawable photo;
    private int photosToDiscoverNb;

    public Game() {
    }

    public Game(String username, Date updatedDate, Drawable photo, int photosToDiscoverNb) {
        this.username = username;
        this.updatedDate = updatedDate;
        this.photo = photo;
        this.photosToDiscoverNb = photosToDiscoverNb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Drawable getPhoto() {
        return photo;
    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public int getPhotosToDiscoverNb() {
        return photosToDiscoverNb;
    }

    public void setPhotosToDiscoverNb(int photosToDiscoverNb) {
        this.photosToDiscoverNb = photosToDiscoverNb;
    }
}
