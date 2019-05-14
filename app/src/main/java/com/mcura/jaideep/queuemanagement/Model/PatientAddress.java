package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientAddress implements Serializable {

    @SerializedName("Address1")
    @Expose
    private String Address1;
    @SerializedName("Address2")
    @Expose
    private String Address2;
    @SerializedName("AddressId")
    @Expose
    private Integer AddressId;
    @SerializedName("AreaId")
    @Expose
    private Integer AreaId;
    @SerializedName("Zipcode")
    @Expose
    private String Zipcode;

    /**
     *
     * @return
     * The Address1
     */
    public String getAddress1() {
        return Address1;
    }

    /**
     *
     * @param Address1
     * The Address1
     */
    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    /**
     *
     * @return
     * The Address2
     */
    public String getAddress2() {
        return Address2;
    }

    /**
     *
     * @param Address2
     * The Address2
     */
    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    /**
     *
     * @return
     * The AddressId
     */
    public Integer getAddressId() {
        return AddressId;
    }

    /**
     *
     * @param AddressId
     * The AddressId
     */
    public void setAddressId(Integer AddressId) {
        this.AddressId = AddressId;
    }

    /**
     *
     * @return
     * The AreaId
     */
    public Integer getAreaId() {
        return AreaId;
    }

    /**
     *
     * @param AreaId
     * The AreaId
     */
    public void setAreaId(Integer AreaId) {
        this.AreaId = AreaId;
    }

    /**
     *
     * @return
     * The Zipcode
     */
    public String getZipcode() {
        return Zipcode;
    }

    /**
     *
     * @param Zipcode
     * The Zipcode
     */
    public void setZipcode(String Zipcode) {
        this.Zipcode = Zipcode;
    }

}