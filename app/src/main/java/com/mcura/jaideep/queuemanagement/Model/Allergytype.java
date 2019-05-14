package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Allergytype implements Serializable {

    @SerializedName("AllergyTypeId")
    @Expose
    private Integer AllergyTypeId;
    @SerializedName("AllergyTypeProperty")
    @Expose
    private String AllergyTypeProperty;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    /**
     *
     * @return
     * The AllergyTypeProperty
     */
    public String getAllergyTypeProperty() {
        return AllergyTypeProperty;
    }

    /**
     *
     * @param AllergyTypeProperty
     * The AllergyTypeProperty
     */
    public void setAllergyTypeProperty(String AllergyTypeProperty) {
        this.AllergyTypeProperty = AllergyTypeProperty;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
