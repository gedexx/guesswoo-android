package com.guesswoo.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "games")
public class Game {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private String username;

    @DatabaseField
    private Date updatedDate;

    @DatabaseField
    private String uriPhoto;

    @DatabaseField
    private int photosToDiscoverNb;

    public Game() {
    }

    public Game(Long id, String username, Date updatedDate, String uriPhoto, int photosToDiscoverNb) {
        this.id = id;
        this.username = username;
        this.updatedDate = updatedDate;
        this.uriPhoto = uriPhoto;
        this.photosToDiscoverNb = photosToDiscoverNb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public int getPhotosToDiscoverNb() {
        return photosToDiscoverNb;
    }

    public void setPhotosToDiscoverNb(int photosToDiscoverNb) {
        this.photosToDiscoverNb = photosToDiscoverNb;
    }
}
