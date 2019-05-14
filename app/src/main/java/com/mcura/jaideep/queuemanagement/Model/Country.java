
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("CntyId")
    @Expose
    private Integer CntyId;
    @SerializedName("ConnectionString")
    @Expose
    private Object ConnectionString;
    @SerializedName("CountryProperty")
    @Expose
    private String CountryProperty;

    /**
     * 
     * @return
     *     The CntyId
     */
    public Integer getCntyId() {
        return CntyId;
    }

    /**
     * 
     * @param CntyId
     *     The CntyId
     */
    public void setCntyId(Integer CntyId) {
        this.CntyId = CntyId;
    }

    /**
     * 
     * @return
     *     The ConnectionString
     */
    public Object getConnectionString() {
        return ConnectionString;
    }

    /**
     * 
     * @param ConnectionString
     *     The ConnectionString
     */
    public void setConnectionString(Object ConnectionString) {
        this.ConnectionString = ConnectionString;
    }

    /**
     * 
     * @return
     *     The CountryProperty
     */
    public String getCountryProperty() {
        return CountryProperty;
    }

    /**
     * 
     * @param CountryProperty
     *     The CountryProperty
     */
    public void setCountryProperty(String CountryProperty) {
        this.CountryProperty = CountryProperty;
    }

}
