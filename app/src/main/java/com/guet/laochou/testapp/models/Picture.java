package com.guet.laochou.testapp.models;

public class Picture {
    private int pictureID; //自增id
    private String ID;     //账号
    private String picture;  //照片
    private String pictureName;//照片名字
    private int likes; //统计点赞数
    private boolean likestatus;

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikestatus() {
        return likestatus;
    }

    public void setLikestatus(boolean likestatus) {
        this.likestatus = likestatus;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
