
package com.mcura.jaideep.queuemanagement.Model.PatDemographics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatDemographicsModel {

    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    @SerializedName("ContactId")
    @Expose
    private Integer contactId;
    @SerializedName("Count")
    @Expose
    private Object count;
    @SerializedName("Dob")
    @Expose
    private String dob;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("GenderId")
    @Expose
    private Integer genderId;
    @SerializedName("HospitalId")
    @Expose
    private String hospitalId;
    @SerializedName("ImagePathId")
    @Expose
    private Object imagePathId;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("PatDemoid")
    @Expose
    private Integer patDemoid;
    @SerializedName("PatPic")
    @Expose
    private String patPic;
    @SerializedName("PatThumbPic")
    @Expose
    private String patThumbPic;
    @SerializedName("Patname")
    @Expose
    private String patname;
    @SerializedName("bloodGroup")
    @Expose
    private Object bloodGroup;
    @SerializedName("bloodGroupId")
    @Expose
    private Object bloodGroupId;
    @SerializedName("patUserRoleId")
    @Expose
    private Object patUserRoleId;
    @SerializedName("regDate")
    @Expose
    private Object regDate;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Object getImagePathId() {
        return imagePathId;
    }

    public void setImagePathId(Object imagePathId) {
        this.imagePathId = imagePathId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPatDemoid() {
        return patDemoid;
    }

    public void setPatDemoid(Integer patDemoid) {
        this.patDemoid = patDemoid;
    }

    public String getPatPic() {
        return patPic;
    }

    public void setPatPic(String patPic) {
        this.patPic = patPic;
    }

    public String getPatThumbPic() {
        return patThumbPic;
    }

    public void setPatThumbPic(String patThumbPic) {
        this.patThumbPic = patThumbPic;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

    public Object getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Object bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Object getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(Object bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public Object getPatUserRoleId() {
        return patUserRoleId;
    }

    public void setPatUserRoleId(Object patUserRoleId) {
        this.patUserRoleId = patUserRoleId;
    }

    public Object getRegDate() {
        return regDate;
    }

    public void setRegDate(Object regDate) {
        this.regDate = regDate;
    }

}
