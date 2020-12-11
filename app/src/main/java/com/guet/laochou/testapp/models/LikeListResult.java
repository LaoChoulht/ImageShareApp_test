package com.guet.laochou.testapp.models;

import java.util.List;

public class LikeListResult extends RemoteReturnResult {
    List<Picture> data;

    public LikeListResult() {
        super();
    }

    public List<Picture> getData() {
        return data;
    }

    public void setData(List<Picture> data) {
        this.data = data;
    }
}
