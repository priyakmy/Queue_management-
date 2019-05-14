package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Allergy_ implements Serializable {

    @SerializedName("AllergyId")
    @Expose
    private Integer AllergyId;
    @SerializedName("AllergyName")
    @Expose
    private String AllergyName;
    @SerializedName("AllergyTypeId")
    @Expose
    private Integer AllergyTypeId;

    /**
     *
     * @return
     * The AllergyId
     */
    public Integer getAllergyId() {
        return AllergyId;
    }

    /**
     *
     * @param AllergyId
     * The AllergyId
     */
    public void setAllergyId(Integer AllergyId) {
        this.AllergyId = AllergyId;
    }

    /**
     *
     * @return
     * The AllergyName
     */
    public String getAllergyName() {
        return AllergyName;
    }

    /**
     *
     * @param AllergyName
     * The AllergyName
     */
    public void setAllergyName(String AllergyName) {
        this.AllergyName = AllergyName;
    }

    /**
     *
     * @return
     * The AllergyTypeId
     */
    public Integer getAllergyTypeId() {
        return AllergyTypeId;
    }

    /**
     *
     * @param AllergyTypeId
     * The AllergyTypeId
     */
    public void setAllergyTypeId(Integer AllergyTypeId) {
        this.AllergyTypeId = AllergyTypeId;
    }

}