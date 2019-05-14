
package com.mcura.jaideep.queuemanagement.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderBoothListModel {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<OrderBoothDatum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<OrderBoothDatum> getData() {
        return data;
    }

    public void setData(List<OrderBoothDatum> data) {
        this.data = data;
    }

}
