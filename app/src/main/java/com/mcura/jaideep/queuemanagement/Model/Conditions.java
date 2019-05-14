package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Conditions implements Serializable {

    @SerializedName("HConId")
    @Expose
    private Integer HConId;
    @SerializedName("HCondition")
    @Expose
    private String HCondition;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The HConId
     */
    public Integer getHConId() {
        return HConId;
    }

    /**
     * @param HConId The HConId
     */
    public void setHConId(Integer HConId) {
        this.HConId = HConId;
    }

    /**
     * @return The HCondition
     */
    public String getHCondition() {
        return HCondition;
    }

    /**
     * @param HCondition The HCondition
     */
    public void setHCondition(String HCondition) {
        this.HCondition = HCondition;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}