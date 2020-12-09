package com.guet.laochou.testapp.models;

import java.util.List;

public class PictureUpload {
    private String token;
    private List<Picture> pictures;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
