
package com.mcura.jaideep.queuemanagement.Model.PatientNoShowModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoShowData {

    @SerializedName("row_Count")
    @Expose
    private Integer rowCount;
    @SerializedName("appId")
    @Expose
    private Integer appId;
    @SerializedName("tokenStatusId")
    @Expose
    private Integer tokenStatusId;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getTokenStatusId() {
        return tokenStatusId;
    }

    public void setTokenStatusId(Integer tokenStatusId) {
        this.tokenStatusId = tokenStatusId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
