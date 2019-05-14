package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConType implements Serializable {

    @SerializedName("HConTypeId")
    @Expose
    private Integer HConTypeId;
    @SerializedName("HConTypeProperty")
    @Expose
    private String HConTypeProperty;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     * @return The HConTypeId
     */
    public Integer getHConTypeId() {
        return HConTypeId;
    }

    /**
     * @param HConTypeId The HConTypeId
     */
    public void setHConTypeId(Integer HConTypeId) {
        this.HConTypeId = HConTypeId;
    }

    /**
     * @return The HConTypeProperty
     */
    public String getHConTypeProperty() {
        return HConTypeProperty;
    }

    /**
     * @param HConTypeProperty The HConTypeProperty
     */
    public void setHConTypeProperty(String HConTypeProperty) {
        this.HConTypeProperty = HConTypeProperty;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}