package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by GauraVachhani on 25/01/16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppointmentModel implements Serializable {

    @SerializedName("AppId")
    @Expose
    private Integer appId;
    @SerializedName("AvlStatusId")
    @Expose
    private Integer avlStatusId;
    @SerializedName("FromTime")
    @Expose
    private String fromTime;
    @SerializedName("TimeTableId")
    @Expose
    private Integer timeTableId;
    @SerializedName("ToTime")
    @Expose
    private String toTime;
    @SerializedName("appbookings")
    @Expose
    private Appbookings appbookings;
    @SerializedName("patdemographics")
    @Expose
    private PatdemographicsModel patdemographics;

    /**
     *
     * @return
     *     The appId
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     *
     * @param appId
     *     The AppId
     */
    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    /**
     *
     * @return
     *     The avlStatusId
     */
    public Integer getAvlStatusId() {
        return avlStatusId;
    }

    /**
     *
     * @param avlStatusId
     *     The AvlStatusId
     */
    public void setAvlStatusId(Integer avlStatusId) {
        this.avlStatusId = avlStatusId;
    }

    /**
     *
     * @return
     *     The fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     *
     * @param fromTime
     *     The FromTime
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     *
     * @return
     *     The timeTableId
     */
    public Integer getTimeTableId() {
        return timeTableId;
    }

    /**
     *
     * @param timeTableId
     *     The TimeTableId
     */
    public void setTimeTableId(Integer timeTableId) {
        this.timeTableId = timeTableId;
    }

    /**
     *
     * @return
     *     The toTime
     */
    public String getToTime() {
        return toTime;
    }

    /**
     *
     * @param toTime
     *     The ToTime
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    /**
     *
     * @return
     *     The appbookings
     */
    public Appbookings getAppbookings() {
        return appbookings;
    }

    /**
     *
     * @param appbookings
     *     The appbookings
     */
    public void setAppbookings(Appbookings appbookings) {
        this.appbookings = appbookings;
    }

    /**
     *
     * @return
     *     The patdemographics
     */
    public PatdemographicsModel getPatdemographics() {
        return patdemographics;
    }

    /**
     *
     * @param patdemographics
     *     The patdemographics
     */
    public void setPatdemographics(PatdemographicsModel patdemographics) {
        this.patdemographics = patdemographics;
    }

}
