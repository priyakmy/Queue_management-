
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSubtenantDetailsModel {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("hospitalFax")
    @Expose
    private String hospitalFax;
    @SerializedName("hospitalFaxExtn")
    @Expose
    private String hospitalFaxExtn;
    @SerializedName("hospitalTelephone")
    @Expose
    private String hospitalTelephone;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("subTenantName")
    @Expose
    private String subTenantName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospitalFax() {
        return hospitalFax;
    }

    public void setHospitalFax(String hospitalFax) {
        this.hospitalFax = hospitalFax;
    }

    public String getHospitalFaxExtn() {
        return hospitalFaxExtn;
    }

    public void setHospitalFaxExtn(String hospitalFaxExtn) {
        this.hospitalFaxExtn = hospitalFaxExtn;
    }

    public String getHospitalTelephone() {
        return hospitalTelephone;
    }

    public void setHospitalTelephone(String hospitalTelephone) {
        this.hospitalTelephone = hospitalTelephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSubTenantName() {
        return subTenantName;
    }

    public void setSubTenantName(String subTenantName) {
        this.subTenantName = subTenantName;
    }

}
