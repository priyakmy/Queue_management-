package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocAssistantMappingModel {
    @SerializedName("data")
    @Expose
    private DataDocAssistantMappingModel data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public DataDocAssistantMappingModel getData() {
        return data;
    }

    public void setData(DataDocAssistantMappingModel data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
