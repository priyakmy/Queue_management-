package com.mcura.jaideep.queuemanagement.Model;


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dataa implements Serializable {

    @SerializedName("PatDemoid")
    @Expose
    private Object patDemoid;
    @SerializedName("opipNo")
    @Expose
    private Object opipNo;
    @SerializedName("Patname")
    @Expose
    private Object patname;
    @SerializedName("Dob")
    @Expose
    private Object dob;
    @SerializedName("GenderId")
    @Expose
    private Object genderId;
    @SerializedName("AddressId")
    @Expose
    private Object addressId;
    @SerializedName("ContactId")
    @Expose
    private Object contactId;
    @SerializedName("PatPic")
    @Expose
    private Object patPic;
    @SerializedName("HospitalId")
    @Expose
    private Object hospitalId;
    @SerializedName("PatThumbPic")
    @Expose
    private Object patThumbPic;
    @SerializedName("sub_tenant_id")
    @Expose
    private Integer subTenantId;
    @SerializedName("EntryTypeId")
    @Expose
    private Integer entryTypeId;
    @SerializedName("RecNatureId")
    @Expose
    private Integer recNatureId;
    @SerializedName("MRNO")
    @Expose
    private Integer mrno;
    @SerializedName("patUserRoleId")
    @Expose
    private Integer patUserRoleId;
    @SerializedName("HospitalNo")
    @Expose
    private Object hospitalNo;
    @SerializedName("Mobile")
    @Expose
    private Object mobile;
    @SerializedName("Email")
    @Expose
    private Object email;
    @SerializedName("Count")
    @Expose
    private Object count;
    @SerializedName("bloodGroupId")
    @Expose
    private Object bloodGroupId;
    @SerializedName("bloodGroup")
    @Expose
    private Object bloodGroup;
    @SerializedName("patmedrecords")
    @Expose
    private List<Patmedrecordd> patmedrecords;
    @SerializedName("imagepath")
    @Expose
    private Object imagepath;
    @SerializedName("PatientAddress")
    @Expose
    private Object patientAddress;
    @SerializedName("PatientContactDetails")
    @Expose
    private Object patientContactDetails;
    @SerializedName("Visits")
    @Expose
    private Object visits;
    @SerializedName("v_HealthCondtion")
    @Expose
    private Object vHealthCondtion;
    @SerializedName("v_PatVitalRecords")
    @Expose
    private Object vPatVitalRecords;
    @SerializedName("v_patallergy")
    @Expose
    private Object vPatallergy;
    @SerializedName("v_socialHistory")
    @Expose
    private Object vSocialHistory;
    @SerializedName("v_disorderAlerts")
    @Expose
    private Object vDisorderAlerts;
    @SerializedName("v_bloodRelParams")
    @Expose
    private Object vBloodRelParams;
    @SerializedName("v_vaccinations")
    @Expose
    private Object vVaccinations;

    public Object getPatDemoid() {
        return patDemoid;
    }

    public void setPatDemoid(Object patDemoid) {
        this.patDemoid = patDemoid;
    }

    public Object getOpipNo() {
        return opipNo;
    }

    public void setOpipNo(Object opipNo) {
        this.opipNo = opipNo;
    }

    public Object getPatname() {
        return patname;
    }

    public void setPatname(Object patname) {
        this.patname = patname;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getGenderId() {
        return genderId;
    }

    public void setGenderId(Object genderId) {
        this.genderId = genderId;
    }

    public Object getAddressId() {
        return addressId;
    }

    public void setAddressId(Object addressId) {
        this.addressId = addressId;
    }

    public Object getContactId() {
        return contactId;
    }

    public void setContactId(Object contactId) {
        this.contactId = contactId;
    }

    public Object getPatPic() {
        return patPic;
    }

    public void setPatPic(Object patPic) {
        this.patPic = patPic;
    }

    public Object getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Object hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Object getPatThumbPic() {
        return patThumbPic;
    }

    public void setPatThumbPic(Object patThumbPic) {
        this.patThumbPic = patThumbPic;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public Integer getEntryTypeId() {
        return entryTypeId;
    }

    public void setEntryTypeId(Integer entryTypeId) {
        this.entryTypeId = entryTypeId;
    }

    public Integer getRecNatureId() {
        return recNatureId;
    }

    public void setRecNatureId(Integer recNatureId) {
        this.recNatureId = recNatureId;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public Integer getPatUserRoleId() {
        return patUserRoleId;
    }

    public void setPatUserRoleId(Integer patUserRoleId) {
        this.patUserRoleId = patUserRoleId;
    }

    public Object getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(Object hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public Object getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(Object bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public Object getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Object bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<Patmedrecordd> getPatmedrecords() {
        return patmedrecords;
    }

    public void setPatmedrecords(List<Patmedrecordd> patmedrecords) {
        this.patmedrecords = patmedrecords;
    }

    public Object getImagepath() {
        return imagepath;
    }

    public void setImagepath(Object imagepath) {
        this.imagepath = imagepath;
    }

    public Object getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(Object patientAddress) {
        this.patientAddress = patientAddress;
    }

    public Object getPatientContactDetails() {
        return patientContactDetails;
    }

    public void setPatientContactDetails(Object patientContactDetails) {
        this.patientContactDetails = patientContactDetails;
    }

    public Object getVisits() {
        return visits;
    }

    public void setVisits(Object visits) {
        this.visits = visits;
    }

    public Object getvHealthCondtion() {
        return vHealthCondtion;
    }

    public void setvHealthCondtion(Object vHealthCondtion) {
        this.vHealthCondtion = vHealthCondtion;
    }

    public Object getvPatVitalRecords() {
        return vPatVitalRecords;
    }

    public void setvPatVitalRecords(Object vPatVitalRecords) {
        this.vPatVitalRecords = vPatVitalRecords;
    }

    public Object getvPatallergy() {
        return vPatallergy;
    }

    public void setvPatallergy(Object vPatallergy) {
        this.vPatallergy = vPatallergy;
    }

    public Object getvSocialHistory() {
        return vSocialHistory;
    }

    public void setvSocialHistory(Object vSocialHistory) {
        this.vSocialHistory = vSocialHistory;
    }

    public Object getvDisorderAlerts() {
        return vDisorderAlerts;
    }

    public void setvDisorderAlerts(Object vDisorderAlerts) {
        this.vDisorderAlerts = vDisorderAlerts;
    }

    public Object getvBloodRelParams() {
        return vBloodRelParams;
    }

    public void setvBloodRelParams(Object vBloodRelParams) {
        this.vBloodRelParams = vBloodRelParams;
    }

    public Object getvVaccinations() {
        return vVaccinations;
    }

    public void setvVaccinations(Object vVaccinations) {
        this.vVaccinations = vVaccinations;
    }

}
