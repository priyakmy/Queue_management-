package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VPatVitalRecord implements Serializable {

    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Mrno")
    @Expose
    private Integer Mrno;
    @SerializedName("Otherinfo")
    @Expose
    private String Otherinfo;
    @SerializedName("PatReadingsId")
    @Expose
    private Integer PatReadingsId;
    @SerializedName("PatVitalId")
    @Expose
    private Integer PatVitalId;
    @SerializedName("Readings")
    @Expose
    private Double Readings;
    @SerializedName("Remarks")
    @Expose
    private String Remarks;
    @SerializedName("Units")
    @Expose
    private String Units;
    @SerializedName("VitalName")
    @Expose
    private String VitalName;
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
     * The Otherinfo
     */
    public String getOtherinfo() {
        return Otherinfo;
    }

    /**
     *
     * @param Otherinfo
     * The Otherinfo
     */
    public void setOtherinfo(String Otherinfo) {
        this.Otherinfo = Otherinfo;
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
     * The Readings
     */
    public Double getReadings() {
        return Readings;
    }

    /**
     *
     * @param Readings
     * The Readings
     */
    public void setReadings(Double Readings) {
        this.Readings = Readings;
    }

    /**
     *
     * @return
     * The Remarks
     */
    public String getRemarks() {
        return Remarks;
    }

    /**
     *
     * @param Remarks
     * The Remarks
     */
    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    /**
     *
     * @return
     * The Units
     */
    public String getUnits() {
        return Units;
    }

    /**
     *
     * @param Units
     * The Units
     */
    public void setUnits(String Units) {
        this.Units = Units;
    }

    /**
     *
     * @return
     * The VitalName
     */
    public String getVitalName() {
        return VitalName;
    }

    /**
     *
     * @param VitalName
     * The VitalName
     */
    public void setVitalName(String VitalName) {
        this.VitalName = VitalName;
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
