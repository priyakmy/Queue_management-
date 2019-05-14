
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenStatusModel {

    @SerializedName("AppId")
    @Expose
    private Integer appId;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("HospitalNo")
    @Expose
    private String hospitalNo;
    @SerializedName("MRNo")
    @Expose
    private Integer mRNo;
    @SerializedName("PatName")
    @Expose
    private String patName;
    @SerializedName("TokenNo")
    @Expose
    private Integer tokenNo;
    @SerializedName("TokenStatus")
    @Expose
    private String tokenStatus;
    @SerializedName("avlstatusid")
    @Expose
    private Integer avlstatusid;
    @SerializedName("fromTime")
    @Expose
    private String fromTime;
    @SerializedName("schedule_id")
    @Expose
    private Integer scheduleId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("toTime")
    @Expose
    private String toTime;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public Integer getMRNo() {
        return mRNo;
    }

    public void setMRNo(Integer mRNo) {
        this.mRNo = mRNo;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public Integer getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Integer getAvlstatusid() {
        return avlstatusid;
    }

    public void setAvlstatusid(Integer avlstatusid) {
        this.avlstatusid = avlstatusid;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
