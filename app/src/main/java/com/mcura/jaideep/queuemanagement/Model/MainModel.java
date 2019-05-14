package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainModel implements Serializable{
    @SerializedName("HospitalNo")
    @Expose
    private String HospitalNo;
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
    private List<Allergy> Allergy = new ArrayList<Allergy>();
    @SerializedName("EntryTypeId")
    @Expose
    private Integer EntryTypeId;
    @SerializedName("HealthCondtions")
    @Expose
    private List<HealthCondtion> HealthCondtions = new ArrayList<HealthCondtion>();
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
    private List<Object> Visits = new ArrayList<Object>();
    @SerializedName("VitalRecords")
    @Expose
    private List<VitalRecord> VitalRecords = new ArrayList<VitalRecord>();
    @SerializedName("imagepath")
    @Expose
    private String imagepath;
    @SerializedName("patmedrecords")
    @Expose
    private List<Patmedrecord> patmedrecords = new ArrayList<Patmedrecord>();
    @SerializedName("sub_tenant_id")
    @Expose
    private Integer subTenantId;
    @SerializedName("v_HealthCondtion")
    @Expose
    private List<VHealthCondtion> vHealthCondtion = new ArrayList<VHealthCondtion>();
    @SerializedName("v_PatVitalRecords")
    @Expose
    private List<VPatVitalRecord> vPatVitalRecords = new ArrayList<VPatVitalRecord>();
    @SerializedName("v_patallergy")
    @Expose
    private List<VPatallergy> vPatallergy = new ArrayList<VPatallergy>();
    @SerializedName("v_patmedrecords")
    @Expose
    private List<VPatmedrecord> vPatmedrecords = new ArrayList<VPatmedrecord>();

    public String getHospitalNo() {
        return HospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        HospitalNo = hospitalNo;
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
     * The ContactId
     */
    public Integer getContactId() {
        return ContactId;
    }

    /**
     *
     * @param ContactId
     * The ContactId
     */
    public void setContactId(Integer ContactId) {
        this.ContactId = ContactId;
    }

    /**
     *
     * @return
     * The Dob
     */
    public String getDob() {
        return Dob;
    }

    /**
     *
     * @param Dob
     * The Dob
     */
    public void setDob(String Dob) {
        this.Dob = Dob;
    }

    /**
     *
     * @return
     * The GenderId
     */
    public Integer getGenderId() {
        return GenderId;
    }

    /**
     *
     * @param GenderId
     * The GenderId
     */
    public void setGenderId(Integer GenderId) {
        this.GenderId = GenderId;
    }

    /**
     *
     * @return
     * The PatDemoid
     */
    public Integer getPatDemoid() {
        return PatDemoid;
    }

    /**
     *
     * @param PatDemoid
     * The PatDemoid
     */
    public void setPatDemoid(Integer PatDemoid) {
        this.PatDemoid = PatDemoid;
    }

    /**
     *
     * @return
     * The Patname
     */
    public String getPatname() {
        return Patname;
    }

    /**
     *
     * @param Patname
     * The Patname
     */
    public void setPatname(String Patname) {
        this.Patname = Patname;
    }

    /**
     *
     * @return
     * The Allergy
     */
    public List<Allergy> getAllergy() {
        return Allergy;
    }

    /**
     *
     * @param Allergy
     * The Allergy
     */
    public void setAllergy(List<Allergy> Allergy) {
        this.Allergy = Allergy;
    }

    /**
     *
     * @return
     * The EntryTypeId
     */
    public Integer getEntryTypeId() {
        return EntryTypeId;
    }

    /**
     *
     * @param EntryTypeId
     * The EntryTypeId
     */
    public void setEntryTypeId(Integer EntryTypeId) {
        this.EntryTypeId = EntryTypeId;
    }

    /**
     *
     * @return
     * The HealthCondtions
     */
    public List<HealthCondtion> getHealthCondtions() {
        return HealthCondtions;
    }

    /**
     *
     * @param HealthCondtions
     * The HealthCondtions
     */
    public void setHealthCondtions(List<HealthCondtion> HealthCondtions) {
        this.HealthCondtions = HealthCondtions;
    }

    /**
     *
     * @return
     * The MRNO
     */
    public Integer getMRNO() {
        return MRNO;
    }

    /**
     *
     * @param MRNO
     * The MRNO
     */
    public void setMRNO(Integer MRNO) {
        this.MRNO = MRNO;
    }

    /**
     *
     * @return
     * The PatientAddress
     */
    public PatientAddress getPatientAddress() {
        return PatientAddress;
    }

    /**
     *
     * @param PatientAddress
     * The PatientAddress
     */
    public void setPatientAddress(PatientAddress PatientAddress) {
        this.PatientAddress = PatientAddress;
    }

    /**
     *
     * @return
     * The PatientContactDetails
     */
    public PatientContactDetails getPatientContactDetails() {
        return PatientContactDetails;
    }

    /**
     *
     * @param PatientContactDetails
     * The PatientContactDetails
     */
    public void setPatientContactDetails(PatientContactDetails PatientContactDetails) {
        this.PatientContactDetails = PatientContactDetails;
    }

    /**
     *
     * @return
     * The RecNatureId
     */
    public Integer getRecNatureId() {
        return RecNatureId;
    }

    /**
     *
     * @param RecNatureId
     * The RecNatureId
     */
    public void setRecNatureId(Integer RecNatureId) {
        this.RecNatureId = RecNatureId;
    }

    /**
     *
     * @return
     * The Visits
     */
    public List<Object> getVisits() {
        return Visits;
    }

    /**
     *
     * @param Visits
     * The Visits
     */
    public void setVisits(List<Object> Visits) {
        this.Visits = Visits;
    }

    /**
     *
     * @return
     * The VitalRecords
     */
    public List<VitalRecord> getVitalRecords() {
        return VitalRecords;
    }

    /**
     *
     * @param VitalRecords
     * The VitalRecords
     */
    public void setVitalRecords(List<VitalRecord> VitalRecords) {
        this.VitalRecords = VitalRecords;
    }

    /**
     *
     * @return
     * The imagepath
     */
    public String getImagepath() {
        return imagepath;
    }

    /**
     *
     * @param imagepath
     * The imagepath
     */
    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    /**
     *
     * @return
     * The patmedrecords
     */
    public List<Patmedrecord> getPatmedrecords() {
        return patmedrecords;
    }

    /**
     *
     * @param patmedrecords
     * The patmedrecords
     */
    public void setPatmedrecords(List<Patmedrecord> patmedrecords) {
        this.patmedrecords = patmedrecords;
    }

    /**
     *
     * @return
     * The subTenantId
     */
    public Integer getSubTenantId() {
        return subTenantId;
    }

    /**
     *
     * @param subTenantId
     * The sub_tenant_id
     */
    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    /**
     *
     * @return
     * The vHealthCondtion
     */
    public List<VHealthCondtion> getVHealthCondtion() {
        return vHealthCondtion;
    }

    /**
     *
     * @param vHealthCondtion
     * The v_HealthCondtion
     */
    public void setVHealthCondtion(List<VHealthCondtion> vHealthCondtion) {
        this.vHealthCondtion = vHealthCondtion;
    }

    /**
     *
     * @return
     * The vPatVitalRecords
     */
    public List<VPatVitalRecord> getVPatVitalRecords() {
        return vPatVitalRecords;
    }

    /**
     *
     * @param vPatVitalRecords
     * The v_PatVitalRecords
     */
    public void setVPatVitalRecords(List<VPatVitalRecord> vPatVitalRecords) {
        this.vPatVitalRecords = vPatVitalRecords;
    }

    /**
     *
     * @return
     * The vPatallergy
     */
    public List<VPatallergy> getVPatallergy() {
        return vPatallergy;
    }

    /**
     *
     * @param vPatallergy
     * The v_patallergy
     */
    public void setVPatallergy(List<VPatallergy> vPatallergy) {
        this.vPatallergy = vPatallergy;
    }

    /**
     *
     * @return
     * The vPatmedrecords
     */
    public List<VPatmedrecord> getVPatmedrecords() {
        return vPatmedrecords;
    }

    /**
     *
     * @param vPatmedrecords
     * The v_patmedrecords
     */
    public void setVPatmedrecords(List<VPatmedrecord> vPatmedrecords) {
        this.vPatmedrecords = vPatmedrecords;
    }

}
