package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetMedicalRecordNatureModel implements Serializable {

    @SerializedName("RecNatureId")
    @Expose
    private Integer recNatureId;
    @SerializedName("RecNatureProperty")
    @Expose
    private String recNatureProperty;

    public Integer getRecNatureId() {
        return recNatureId;
    }

    public void setRecNatureId(Integer recNatureId) {
        this.recNatureId = recNatureId;
    }

    public String getRecNatureProperty() {
        return recNatureProperty;
    }

    public void setRecNatureProperty(String recNatureProperty) {
        this.recNatureProperty = recNatureProperty;
    }

}
