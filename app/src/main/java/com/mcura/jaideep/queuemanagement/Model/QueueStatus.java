
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueStatus {

    @SerializedName("AppId")
    @Expose
    private Integer appId;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("HospitalNo")
    @Expose
    private Object hospitalNo;
    @SerializedName("MRNo")
    @Expose
    private Integer mRNo;
    @SerializedName("PatName")
    @Expose
    private Object patName;
    @SerializedName("TokenNo")
    @Expose
    private Integer tokenNo;
    @SerializedName("TokenStatus")
    @Expose
    private Object tokenStatus;
    @SerializedName("avlstatusid")
    @Expose
    private Object avlstatusid;
    @SerializedName("fromTime")
    @Expose
    private Object fromTime;
    @SerializedName("schedule_id")
    @Expose
    private Object scheduleId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("toTime")
    @Expose
    private Object toTime;

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

    public Object getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(Object hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public Integer getMRNo() {
        return mRNo;
    }

    public void setMRNo(Integer mRNo) {
        this.mRNo = mRNo;
    }

    public Object getPatName() {
        return patName;
    }

    public void setPatName(Object patName) {
        this.patName = patName;
    }

    public Integer getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }

    public Object getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(Object tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Object getAvlstatusid() {
        return avlstatusid;
    }

    public void setAvlstatusid(Object avlstatusid) {
        this.avlstatusid = avlstatusid;
    }

    public Object getFromTime() {
        return fromTime;
    }

    public void setFromTime(Object fromTime) {
        this.fromTime = fromTime;
    }

    public Object getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Object scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getToTime() {
        return toTime;
    }

    public void setToTime(Object toTime) {
        this.toTime = toTime;
    }

}
