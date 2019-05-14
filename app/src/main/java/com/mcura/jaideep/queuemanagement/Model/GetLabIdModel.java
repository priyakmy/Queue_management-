
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLabIdModel {

    @SerializedName("AddressID")
    @Expose
    private Integer addressID;
    @SerializedName("ContactID")
    @Expose
    private Integer contactID;
    @SerializedName("FacilityTypeId")
    @Expose
    private Object facilityTypeId;
    @SerializedName("FacilityTypeName")
    @Expose
    private Object facilityTypeName;
    @SerializedName("SubTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("SubTenantName")
    @Expose
    private String subTenantName;

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public Object getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(Object facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }

    public Object getFacilityTypeName() {
        return facilityTypeName;
    }

    public void setFacilityTypeName(Object facilityTypeName) {
        this.facilityTypeName = facilityTypeName;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public String getSubTenantName() {
        return subTenantName;
    }

    public void setSubTenantName(String subTenantName) {
        this.subTenantName = subTenantName;
    }

}
