
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentNature {

    @SerializedName("AppNature")
    @Expose
    private String AppNature;
    @SerializedName("AppNatureIdProperty")
    @Expose
    private Integer AppNatureIdProperty;

    /**
     * 
     * @return
     *     The AppNature
     */
    public String getAppNature() {
        return AppNature;
    }

    /**
     * 
     * @param AppNature
     *     The AppNature
     */
    public void setAppNature(String AppNature) {
        this.AppNature = AppNature;
    }

    /**
     * 
     * @return
     *     The AppNatureIdProperty
     */
    public Integer getAppNatureIdProperty() {
        return AppNatureIdProperty;
    }

    /**
     * 
     * @param AppNatureIdProperty
     *     The AppNatureIdProperty
     */
    public void setAppNatureIdProperty(Integer AppNatureIdProperty) {
        this.AppNatureIdProperty = AppNatureIdProperty;
    }

}
