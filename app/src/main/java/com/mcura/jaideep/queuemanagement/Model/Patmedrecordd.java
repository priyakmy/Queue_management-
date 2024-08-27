package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Patmedrecordd implements Serializable {

    @SerializedName("RecordId")
    @Expose
    private Integer recordId;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("postDate")
    @Expose
    private Object postDate;
    @SerializedName("UserRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("RecNatureId")
    @Expose
    private Integer recNatureId;
    @SerializedName("EntryTypeId")
    @Expose
    private Integer entryTypeId;
    @SerializedName("DataType")
    @Expose
    private Object dataType;
    @SerializedName("timespan")
    @Expose
    private Object timespan;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("sessionkey")
    @Expose
    private Object sessionkey;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("recPath")
    @Expose
    private String recPath;
    @SerializedName("recType")
    @Expose
    private Integer recType;
    @SerializedName("ConnectionString")
    @Expose
    private Object connectionString;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getPostDate() {
        return postDate;
    }

    public void setPostDate(Object postDate) {
        this.postDate = postDate;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getRecNatureId() {
        return recNatureId;
    }

    public void setRecNatureId(Integer recNatureId) {
        this.recNatureId = recNatureId;
    }

    public Integer getEntryTypeId() {
        return entryTypeId;
    }

    public void setEntryTypeId(Integer entryTypeId) {
        this.entryTypeId = entryTypeId;
    }

    public Object getDataType() {
        return dataType;
    }

    public void setDataType(Object dataType) {
        this.dataType = dataType;
    }

    public Object getTimespan() {
        return timespan;
    }

    public void setTimespan(Object timespan) {
        this.timespan = timespan;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Object getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(Object sessionkey) {
        this.sessionkey = sessionkey;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRecPath() {
        return recPath;
    }

    public void setRecPath(String recPath) {
        this.recPath = recPath;
    }

    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
    }

    public Object getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(Object connectionString) {
        this.connectionString = connectionString;
    }
}
