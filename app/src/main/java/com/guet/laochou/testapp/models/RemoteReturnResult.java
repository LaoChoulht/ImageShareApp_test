package com.guet.laochou.testapp.models;

public class RemoteReturnResult {
    private int status;
    private String msg;

    public RemoteReturnResult(){
        setStatus(0);
        setMsg(" ");
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
