
package com.mcura.jaideep.queuemanagement.Model.PatientNoShowModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostPatientNoShowModelResponse {

    @SerializedName("noShowData")
    @Expose
    private NoShowData noShowData;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public NoShowData getNoShowData() {
        return noShowData;
    }

    public void setNoShowData(NoShowData noShowData) {
        this.noShowData = noShowData;
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
