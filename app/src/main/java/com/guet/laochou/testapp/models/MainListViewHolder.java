package com.guet.laochou.testapp.models;

import android.widget.ImageView;
import android.widget.TextView;

public class MainListViewHolder {

    public ImageView iv_thumbnail,iv_likeBtn,iv_downloadBtn;
    public TextView tv_imageID;
    public TextView tv_likeNum;


    public ImageView getIv_thumbnail() {
        return iv_thumbnail;
    }

    public void setIv_thumbnail(ImageView iv_thumbnail) {
        this.iv_thumbnail = iv_thumbnail;
    }

    public TextView getTv_imageID() {
        return tv_imageID;
    }

    public void setTv_imageID(TextView tv_imageID) {
        this.tv_imageID = tv_imageID;
    }

    public TextView getTv_likeNum() {
        return tv_likeNum;
    }

    public void setTv_likeNum(TextView tv_likeNum) {
        this.tv_likeNum = tv_likeNum;
    }
}
