
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("CityId")
    @Expose
    private Integer CityId;
    @SerializedName("StateId")
    @Expose
    private Integer StateId;

    /**
     * 
     * @return
     *     The City
     */
    public String getCity() {
        return City;
    }

    /**
     * 
     * @param City
     *     The City
     */
    public void setCity(String City) {
        this.City = City;
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

    /**
     * 
     * @return
     *     The StateId
     */
    public Integer getStateId() {
        return StateId;
    }

    /**
     * 
     * @param StateId
     *     The StateId
     */
    public void setStateId(Integer StateId) {
        this.StateId = StateId;
    }

}
