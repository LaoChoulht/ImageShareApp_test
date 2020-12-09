package com.guet.laochou.testapp.models;

public class Picture {
    private byte[] Picture;
    private String pictureName;

    public byte[] getPicture() {
        return Picture;
    }

    public void setPicture(byte[] picture) {
        Picture = picture;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
