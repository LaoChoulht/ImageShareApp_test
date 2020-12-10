package com.guet.laochou.testapp.models;

import android.graphics.Bitmap;


public class MyImage {
    Bitmap original;
    String imageID;
    String likes;
    boolean liked = false;

    public Bitmap getOriginal() {
        return original;
    }

    public void setOriginal(Bitmap original) {
        this.original = original;
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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
