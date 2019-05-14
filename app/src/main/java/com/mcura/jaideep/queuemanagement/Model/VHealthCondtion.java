package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VHealthCondtion implements Serializable {

    @SerializedName("ConType")
    @Expose
    private ConType ConType;
    @SerializedName("Conditions")
    @Expose
    private Conditions Conditions;
    @SerializedName("EnteredOn")
    @Expose
    private String EnteredOn;
    @SerializedName("Existsfrom")
    @Expose
    private String Existsfrom;
    @SerializedName("HConId")
    @Expose
    private Integer HConId;
    @SerializedName("HConTypeId")
    @Expose
    private Integer HConTypeId;
    @SerializedName("Mrno")
    @Expose
    private Integer Mrno;

    /**
     *
     * @return
     * The ConType
     */
    public ConType getConType() {
        return ConType;
    }

    /**
     *
     * @param ConType
     * The ConType
     */
    public void setConType(ConType ConType) {
        this.ConType = ConType;
    }

    /**
     *
     * @return
     * The Conditions
     */
    public Conditions getConditions() {
        return Conditions;
    }

    /**
     *
     * @param Conditions
     * The Conditions
     */
    public void setConditions(Conditions Conditions) {
        this.Conditions = Conditions;
    }

    /**
     *
     * @return
     * The EnteredOn
     */
    public String getEnteredOn() {
        return EnteredOn;
    }

    /**
     *
     * @param EnteredOn
     * The EnteredOn
     */
    public void setEnteredOn(String EnteredOn) {
        this.EnteredOn = EnteredOn;
    }

    /**
     *
     * @return
     * The Existsfrom
     */
    public String getExistsfrom() {
        return Existsfrom;
    }

    /**
     *
     * @param Existsfrom
     * The Existsfrom
     */
    public void setExistsfrom(String Existsfrom) {
        this.Existsfrom = Existsfrom;
    }

    /**
     *
     * @return
     * The HConId
     */
    public Integer getHConId() {
        return HConId;
    }

    /**
     *
     * @param HConId
     * The HConId
     */
    public void setHConId(Integer HConId) {
        this.HConId = HConId;
    }

    /**
     *
     * @return
     * The HConTypeId
     */
    public Integer getHConTypeId() {
        return HConTypeId;
    }

    /**
     *
     * @param HConTypeId
     * The HConTypeId
     */
    public void setHConTypeId(Integer HConTypeId) {
        this.HConTypeId = HConTypeId;
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

}
