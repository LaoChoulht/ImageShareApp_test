package com.guet.laochou.testapp.models;

import java.io.Serializable;

public class LoginResult extends RemoteReturnResult implements Serializable {
    private String data;

    public LoginResult() {
        super();
        setData(" ");
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
