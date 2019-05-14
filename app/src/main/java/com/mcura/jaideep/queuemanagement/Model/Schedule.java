
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("CurrentTokenNo")
    @Expose
    private Integer currentTokenNo;
    @SerializedName("FromTime")
    @Expose
    private String fromTime;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("NextTokenNo")
    @Expose
    private Integer nextTokenNo;
    @SerializedName("ScheduleId")
    @Expose
    private Integer scheduleId;
    @SerializedName("ScheduleName")
    @Expose
    private String scheduleName;
    @SerializedName("ToTime")
    @Expose
    private String toTime;

    /**
     * 
     * @return
     *     The currentTokenNo
     */
    public Integer getCurrentTokenNo() {
        return currentTokenNo;
    }

    /**
     * 
     * @param currentTokenNo
     *     The CurrentTokenNo
     */
    public void setCurrentTokenNo(Integer currentTokenNo) {
        this.currentTokenNo = currentTokenNo;
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
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The nextTokenNo
     */
    public Integer getNextTokenNo() {
        return nextTokenNo;
    }

    /**
     * 
     * @param nextTokenNo
     *     The NextTokenNo
     */
    public void setNextTokenNo(Integer nextTokenNo) {
        this.nextTokenNo = nextTokenNo;
    }

    /**
     * 
     * @return
     *     The scheduleId
     */
    public Integer getScheduleId() {
        return scheduleId;
    }

    /**
     * 
     * @param scheduleId
     *     The ScheduleId
     */
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * 
     * @return
     *     The scheduleName
     */
    public String getScheduleName() {
        return scheduleName;
    }

    /**
     * 
     * @param scheduleName
     *     The ScheduleName
     */
    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
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

}
