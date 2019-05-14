
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

    @SerializedName("Area")
    @Expose
    private String Area;
    @SerializedName("AreaId")
    @Expose
    private Integer AreaId;
    @SerializedName("CityId")
    @Expose
    private Integer CityId;

    /**
     * 
     * @return
     *     The Area
     */
    public String getArea() {
        return Area;
    }

    /**
     * 
     * @param Area
     *     The Area
     */
    public void setArea(String Area) {
        this.Area = Area;
    }

    /**
     * 
     * @return
     *     The AreaId
     */
    public Integer getAreaId() {
        return AreaId;
    }

    /**
     * 
     * @param AreaId
     *     The AreaId
     */
    public void setAreaId(Integer AreaId) {
        this.AreaId = AreaId;
    }

    /**
     * 
     * @return
     *     The CityId
     */
    public Integer getCityId() {
        return CityId;
    }

    /**
     * 
     * @param CityId
     *     The CityId
     */
    public void setCityId(Integer CityId) {
        this.CityId = CityId;
    }

}
