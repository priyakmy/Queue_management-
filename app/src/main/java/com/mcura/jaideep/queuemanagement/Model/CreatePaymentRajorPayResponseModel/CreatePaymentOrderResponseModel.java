
package com.mcura.jaideep.queuemanagement.Model.CreatePaymentRajorPayResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatePaymentOrderResponseModel {

    @SerializedName("data")
    @Expose
    private Dataum data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Dataum getData() {
        return data;
    }

    public void setData(Dataum data) {
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
