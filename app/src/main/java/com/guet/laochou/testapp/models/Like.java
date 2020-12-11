package com.guet.laochou.testapp.models;

public class Like {
    private String token;   //token
    private int pictureID;  //照片ID

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }
}
