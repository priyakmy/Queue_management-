package com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostTrackerResponse {
    @SerializedName("statusId")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;

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
