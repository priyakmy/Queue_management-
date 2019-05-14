package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VitalRecord implements Serializable {

    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Mrno")
    @Expose
    private Integer Mrno;
    @SerializedName("PatReadingsId")
    @Expose
    private Integer PatReadingsId;
    @SerializedName("PatVitalId")
    @Expose
    private Integer PatVitalId;
    @SerializedName("VitalNatureId")
    @Expose
    private Integer VitalNatureId;

    /**
     *
     * @return
     * The Date
     */
    public String getDate() {
        return Date;
    }

    /**
     *
     * @param Date
     * The Date
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     *
     * @return
     * The Mrno
     */
    public Integer getMrno() {
        return Mrno;
    }

    /**
     *
     * @param Mrno
     * The Mrno
     */
    public void setMrno(Integer Mrno) {
        this.Mrno = Mrno;
    }

    /**
     *
     * @return
     * The PatReadingsId
     */
    public Integer getPatReadingsId() {
        return PatReadingsId;
    }

    /**
     *
     * @param PatReadingsId
     * The PatReadingsId
     */
    public void setPatReadingsId(Integer PatReadingsId) {
        this.PatReadingsId = PatReadingsId;
    }

    /**
     *
     * @return
     * The PatVitalId
     */
    public Integer getPatVitalId() {
        return PatVitalId;
    }

    /**
     *
     * @param PatVitalId
     * The PatVitalId
     */
    public void setPatVitalId(Integer PatVitalId) {
        this.PatVitalId = PatVitalId;
    }

    /**
     *
     * @return
     * The VitalNatureId
     */
    public Integer getVitalNatureId() {
        return VitalNatureId;
    }

    /**
     *
     * @param VitalNatureId
     * The VitalNatureId
     */
    public void setVitalNatureId(Integer VitalNatureId) {
        this.VitalNatureId = VitalNatureId;
    }

}