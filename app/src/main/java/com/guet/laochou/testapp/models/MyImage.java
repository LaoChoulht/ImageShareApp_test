package com.guet.laochou.testapp.models;

import android.graphics.Bitmap;

public class MyImage {
    Bitmap original;
    int thumbnailResID;
    String imageID;
    String likes;

    public Bitmap getOriginal() {
        return original;
    }

    public void setOriginal(Bitmap original) {
        this.original = original;
    }

    public int getThumbnailResID() {
        return thumbnailResID;
    }

    public void setThumbnailResID(int thumbnailResID) {
        this.thumbnailResID = thumbnailResID;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
