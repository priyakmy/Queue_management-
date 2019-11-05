
package com.mcura.jaideep.queuemanagement.Model.PatientVerificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatVerificationResponseModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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
