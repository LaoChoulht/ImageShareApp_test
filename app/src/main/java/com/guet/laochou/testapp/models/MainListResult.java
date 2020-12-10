package com.guet.laochou.testapp.models;

import java.util.List;

public class MainListResult extends RemoteReturnResult {
    List<Picture> data;

    public MainListResult(){
        super();
//        pictures = new ArrayList<>();
    }
    public List<Picture> getData() {
        return data;
    }

    public void setData(List<Picture> data) {
        this.data = data;
    }
}
