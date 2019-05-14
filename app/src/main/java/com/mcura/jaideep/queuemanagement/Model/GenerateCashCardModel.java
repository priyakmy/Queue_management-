package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mCURA1 on 3/10/2017.
 */

public class GenerateCashCardModel {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("msg")
    @Expose
    private Integer msg;
    @SerializedName("status")
    @Expose
    private boolean status;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public Integer getMsg() {
        return msg;
    }

    public void setMsg(Integer msg) {
        this.msg = msg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
