package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Allergy implements Serializable{

    @SerializedName("AllergyId")
    @Expose
    private Integer AllergyId;
    @SerializedName("CurrentStatusId")
    @Expose
    private Integer CurrentStatusId;
    @SerializedName("Existsfrom")
    @Expose
    private String Existsfrom;
    @SerializedName("Mrno")
    @Expose
    private Integer Mrno;

    /**
     * @return The AllergyId
     */
    public Integer getAllergyId() {
        return AllergyId;
    }

    /**
     * @param AllergyId The AllergyId
     */
    public void setAllergyId(Integer AllergyId) {
        this.AllergyId = AllergyId;
    }

    /**
     * @return The CurrentStatusId
     */
    public Integer getCurrentStatusId() {
        return CurrentStatusId;
    }

    /**
     * @param CurrentStatusId The CurrentStatusId
     */
    public void setCurrentStatusId(Integer CurrentStatusId) {
        this.CurrentStatusId = CurrentStatusId;
    }

    /**
     * @return The Existsfrom
     */
    public String getExistsfrom() {
        return Existsfrom;
    }

    /**
     * @param Existsfrom The Existsfrom
     */
    public void setExistsfrom(String Existsfrom) {
        this.Existsfrom = Existsfrom;
    }

    /**
     * @return The Mrno
     */
    public Integer getMrno() {
        return Mrno;
    }

    /**
     * @param Mrno The Mrno
     */
    public void setMrno(Integer Mrno) {
        this.Mrno = Mrno;
    }

}
