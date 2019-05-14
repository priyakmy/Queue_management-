
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientSearchModel implements Serializable{

    @SerializedName("AddressId")
    @Expose
    private Integer AddressId;
    @SerializedName("ContactId")
    @Expose
    private Integer ContactId;
    @SerializedName("Dob")
    @Expose
    private String Dob;
    @SerializedName("GenderId")
    @Expose
    private Integer GenderId;
    @SerializedName("PatDemoid")
    @Expose
    private Integer PatDemoid;
    @SerializedName("Patname")
    @Expose
    private String Patname;
    @SerializedName("Allergy")
    @Expose
    private Object Allergy;
    @SerializedName("EntryTypeId")
    @Expose
    private Integer EntryTypeId;
    @SerializedName("HealthCondtions")
    @Expose
    private Object HealthCondtions;
    @SerializedName("HospitalNo")
    @Expose
    private String HospitalNo;
    @SerializedName("MRNO")
    @Expose
    private Integer MRNO;
    @SerializedName("PatientAddress")
    @Expose
    private PatientAddress PatientAddress;
    @SerializedName("PatientContactDetails")
    @Expose
    private PatientContactDetails PatientContactDetails;
    @SerializedName("RecNatureId")
    @Expose
    private Integer RecNatureId;
    @SerializedName("Visits")
    @Expose
    private Object Visits;
    @SerializedName("VitalRecords")
    @Expose
    private Object VitalRecords;
    @SerializedName("imagepath")
    @Expose
    private Object imagepath;
    @SerializedName("patmedrecords")
    @Expose
    private Object patmedrecords;
    @SerializedName("sub_tenant_id")
    @Expose
    private Integer subTenantId;
    @SerializedName("v_HealthCondtion")
    @Expose
    private Object vHealthCondtion;
    @SerializedName("v_PatVitalRecords")
    @Expose
    private Object vPatVitalRecords;
    @SerializedName("v_patallergy")
    @Expose
    private Object vPatallergy;
    @SerializedName("v_patmedrecords")
    @Expose
    private Object vPatmedrecords;

    /**
     * 
     * @return
     *     The AddressId
     */
    public Integer getAddressId() {
        return AddressId;
    }

    /**
     * 
     * @param AddressId
     *     The AddressId
     */
    public void setAddressId(Integer AddressId) {
        this.AddressId = AddressId;
    }

    /**
     * 
     * @return
     *     The ContactId
     */
    public Integer getContactId() {
        return ContactId;
    }

    /**
     * 
     * @param ContactId
     *     The ContactId
     */
    public void setContactId(Integer ContactId) {
        this.ContactId = ContactId;
    }

    /**
     * 
     * @return
     *     The Dob
     */
    public String getDob() {
        return Dob;
    }

    /**
     * 
     * @param Dob
     *     The Dob
     */
    public void setDob(String Dob) {
        this.Dob = Dob;
    }

    /**
     * 
     * @return
     *     The GenderId
     */
    public Integer getGenderId() {
        return GenderId;
    }

    /**
     * 
     * @param GenderId
     *     The GenderId
     */
    public void setGenderId(Integer GenderId) {
        this.GenderId = GenderId;
    }

    /**
     * 
     * @return
     *     The PatDemoid
     */
    public Integer getPatDemoid() {
        return PatDemoid;
    }

    /**
     * 
     * @param PatDemoid
     *     The PatDemoid
     */
    public void setPatDemoid(Integer PatDemoid) {
        this.PatDemoid = PatDemoid;
    }

    /**
     * 
     * @return
     *     The Patname
     */
    public String getPatname() {
        return Patname;
    }

    /**
     * 
     * @param Patname
     *     The Patname
     */
    public void setPatname(String Patname) {
        this.Patname = Patname;
    }

    /**
     * 
     * @return
     *     The Allergy
     */
    public Object getAllergy() {
        return Allergy;
    }

    /**
     * 
     * @param Allergy
     *     The Allergy
     */
    public void setAllergy(Object Allergy) {
        this.Allergy = Allergy;
    }

    /**
     * 
     * @return
     *     The EntryTypeId
     */
    public Integer getEntryTypeId() {
        return EntryTypeId;
    }

    /**
     * 
     * @param EntryTypeId
     *     The EntryTypeId
     */
    public void setEntryTypeId(Integer EntryTypeId) {
        this.EntryTypeId = EntryTypeId;
    }

    /**
     * 
     * @return
     *     The HealthCondtions
     */
    public Object getHealthCondtions() {
        return HealthCondtions;
    }

    /**
     * 
     * @param HealthCondtions
     *     The HealthCondtions
     */
    public void setHealthCondtions(Object HealthCondtions) {
        this.HealthCondtions = HealthCondtions;
    }

    /**
     * 
     * @return
     *     The HospitalNo
     */
    public String getHospitalNo() {
        return HospitalNo;
    }

    /**
     * 
     * @param HospitalNo
     *     The HospitalNo
     */
    public void setHospitalNo(String HospitalNo) {
        this.HospitalNo = HospitalNo;
    }

    /**
     * 
     * @return
     *     The MRNO
     */
    public Integer getMRNO() {
        return MRNO;
    }

    /**
     * 
     * @param MRNO
     *     The MRNO
     */
    public void setMRNO(Integer MRNO) {
        this.MRNO = MRNO;
    }

    /**
     * 
     * @return
     *     The PatientAddress
     */
    public PatientAddress getPatientAddress() {
        return PatientAddress;
    }

    /**
     * 
     * @param PatientAddress
     *     The PatientAddress
     */
    public void setPatientAddress(PatientAddress PatientAddress) {
        this.PatientAddress = PatientAddress;
    }

    /**
     * 
     * @return
     *     The PatientContactDetails
     */
    public PatientContactDetails getPatientContactDetails() {
        return PatientContactDetails;
    }

    /**
     * 
     * @param PatientContactDetails
     *     The PatientContactDetails
     */
    public void setPatientContactDetails(PatientContactDetails PatientContactDetails) {
        this.PatientContactDetails = PatientContactDetails;
    }

    /**
     * 
     * @return
     *     The RecNatureId
     */
    public Integer getRecNatureId() {
        return RecNatureId;
    }

    /**
     * 
     * @param RecNatureId
     *     The RecNatureId
     */
    public void setRecNatureId(Integer RecNatureId) {
        this.RecNatureId = RecNatureId;
    }

    /**
     * 
     * @return
     *     The Visits
     */
    public Object getVisits() {
        return Visits;
    }

    /**
     * 
     * @param Visits
     *     The Visits
     */
    public void setVisits(Object Visits) {
        this.Visits = Visits;
    }

    /**
     * 
     * @return
     *     The VitalRecords
     */
    public Object getVitalRecords() {
        return VitalRecords;
    }

    /**
     * 
     * @param VitalRecords
     *     The VitalRecords
     */
    public void setVitalRecords(Object VitalRecords) {
        this.VitalRecords = VitalRecords;
    }

    /**
     * 
     * @return
     *     The imagepath
     */
    public Object getImagepath() {
        return imagepath;
    }

    /**
     * 
     * @param imagepath
     *     The imagepath
     */
    public void setImagepath(Object imagepath) {
        this.imagepath = imagepath;
    }

    /**
     * 
     * @return
     *     The patmedrecords
     */
    public Object getPatmedrecords() {
        return patmedrecords;
    }

    /**
     * 
     * @param patmedrecords
     *     The patmedrecords
     */
    public void setPatmedrecords(Object patmedrecords) {
        this.patmedrecords = patmedrecords;
    }

    /**
     * 
     * @return
     *     The subTenantId
     */
    public Integer getSubTenantId() {
        return subTenantId;
    }

    /**
     * 
     * @param subTenantId
     *     The sub_tenant_id
     */
    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    /**
     * 
     * @return
     *     The vHealthCondtion
     */
    public Object getVHealthCondtion() {
        return vHealthCondtion;
    }

    /**
     * 
     * @param vHealthCondtion
     *     The v_HealthCondtion
     */
    public void setVHealthCondtion(Object vHealthCondtion) {
        this.vHealthCondtion = vHealthCondtion;
    }

    /**
     * 
     * @return
     *     The vPatVitalRecords
     */
    public Object getVPatVitalRecords() {
        return vPatVitalRecords;
    }

    /**
     * 
     * @param vPatVitalRecords
     *     The v_PatVitalRecords
     */
    public void setVPatVitalRecords(Object vPatVitalRecords) {
        this.vPatVitalRecords = vPatVitalRecords;
    }

    /**
     * 
     * @return
     *     The vPatallergy
     */
    public Object getVPatallergy() {
        return vPatallergy;
    }

    /**
     * 
     * @param vPatallergy
     *     The v_patallergy
     */
    public void setVPatallergy(Object vPatallergy) {
        this.vPatallergy = vPatallergy;
    }

    /**
     * 
     * @return
     *     The vPatmedrecords
     */
    public Object getVPatmedrecords() {
        return vPatmedrecords;
    }

    /**
     * 
     * @param vPatmedrecords
     *     The v_patmedrecords
     */
    public void setVPatmedrecords(Object vPatmedrecords) {
        this.vPatmedrecords = vPatmedrecords;
    }

}
