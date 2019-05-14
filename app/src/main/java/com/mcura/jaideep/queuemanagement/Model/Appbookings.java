
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appbookings implements Serializable {

    @SerializedName("AppId")
    @Expose
    private Object appId;
    @SerializedName("AppNatureId")
    @Expose
    private Integer appNatureId;
    @SerializedName("BookingId")
    @Expose
    private Integer bookingId;
    @SerializedName("CurrentStatusId")
    @Expose
    private Integer currentStatusId;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("Others")
    @Expose
    private String others;

    /**
     * 
     * @return
     *     The appId
     */
    public Object getAppId() {
        return appId;
    }

    /**
     * 
     * @param appId
     *     The AppId
     */
    public void setAppId(Object appId) {
        this.appId = appId;
    }

    /**
     * 
     * @return
     *     The appNatureId
     */
    public Integer getAppNatureId() {
        return appNatureId;
    }

    /**
     * 
     * @param appNatureId
     *     The AppNatureId
     */
    public void setAppNatureId(Integer appNatureId) {
        this.appNatureId = appNatureId;
    }

    /**
     * 
     * @return
     *     The bookingId
     */
    public Integer getBookingId() {
        return bookingId;
    }

    /**
     * 
     * @param bookingId
     *     The BookingId
     */
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * 
     * @return
     *     The currentStatusId
     */
    public Integer getCurrentStatusId() {
        return currentStatusId;
    }

    /**
     * 
     * @param currentStatusId
     *     The CurrentStatusId
     */
    public void setCurrentStatusId(Integer currentStatusId) {
        this.currentStatusId = currentStatusId;
    }

    /**
     * 
     * @return
     *     The mrno
     */
    public Integer getMrno() {
        return mrno;
    }

    /**
     * 
     * @param mrno
     *     The Mrno
     */
    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    /**
     * 
     * @return
     *     The others
     */
    public String getOthers() {
        return others;
    }

    /**
     * 
     * @param others
     *     The Others
     */
    public void setOthers(String others) {
        this.others = others;
    }

}
