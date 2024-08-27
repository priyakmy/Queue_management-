package com.mcura.jaideep.queuemanagement.Model;
import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PatLatestRecord implements Serializable{

    @SerializedName("data")
    @Expose
    private Dataa data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Dataa getData() {
        return data;
    }

    public void setData(Dataa data) {
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

 /*   @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("recPath")
    @Expose
    private String recPath;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getRecPath() {
        return recPath;
    }

    public void setRecPath(String recPath) {
        this.recPath = recPath;
    }*/
}


