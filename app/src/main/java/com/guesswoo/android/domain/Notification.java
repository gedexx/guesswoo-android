package com.guesswoo.android.domain;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class Notification {

    private String type;
    private Date updatedDate;
    private Drawable photo;

    public Notification() {
    }

    public Notification(String type, Date updatedDate, Drawable photo) {
        this.type = type;
        this.updatedDate = updatedDate;
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
