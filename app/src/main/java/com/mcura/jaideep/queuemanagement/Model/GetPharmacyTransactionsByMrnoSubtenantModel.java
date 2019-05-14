
package com.mcura.jaideep.queuemanagement.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPharmacyTransactionsByMrnoSubtenantModel {

    @SerializedName("data")
    @Expose
    private List<PharmacyTransactionDatum> data = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<PharmacyTransactionDatum> getData() {
        return data;
    }

    public void setData(List<PharmacyTransactionDatum> data) {
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
