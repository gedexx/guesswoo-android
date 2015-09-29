package com.guesswoo.android.service.rest.response;

public class BoardResponse {

    private String photo;
    private boolean excluded;

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
