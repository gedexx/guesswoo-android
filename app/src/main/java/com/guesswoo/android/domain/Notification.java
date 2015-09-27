package com.guesswoo.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "notifications")
public class Notification {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private String type;

    @DatabaseField
    private Date updatedDate;

    @DatabaseField
    private String uriPhoto;

    public Notification() {
    }

    public Notification(Long id, String type, Date updatedDate, String uriPhoto) {
        this.id = id;
        this.type = type;
        this.updatedDate = updatedDate;
        this.uriPhoto = uriPhoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }
}
