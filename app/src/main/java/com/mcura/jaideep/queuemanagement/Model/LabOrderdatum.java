
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabOrderdatum {

    @SerializedName("Against_sub_tenant_id")
    @Expose
    private Integer againstSubTenantId;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Days")
    @Expose
    private Integer days;
    @SerializedName("EST_Billno")
    @Expose
    private Integer eSTBillno;
    @SerializedName("LabGrpTest")
    @Expose
    private Object labGrpTest;
    @SerializedName("LabOrderId")
    @Expose
    private Integer labOrderId;
    @SerializedName("LabPackageTest")
    @Expose
    private Object labPackageTest;
    @SerializedName("LabPharmacyType")
    @Expose
    private Integer labPharmacyType;
    @SerializedName("LabTestList")
    @Expose
    private Object labTestList;
    @SerializedName("Laborder_id")
    @Expose
    private Integer laborderId;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("Pat_demoid")
    @Expose
    private Integer patDemoid;
    @SerializedName("Patname")
    @Expose
    private String patname;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("contact_id")
    @Expose
    private Integer contactId;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("entry_type_id")
    @Expose
    private Integer entryTypeId;
    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("gender_id")
    @Expose
    private Integer genderId;
    @SerializedName("laborders")
    @Expose
    private Object laborders;
    @SerializedName("laborderstatus")
    @Expose
    private Object laborderstatus;
    @SerializedName("orderBy_sub_tenant_id")
    @Expose
    private Integer orderBySubTenantId;
    @SerializedName("patdemographics")
    @Expose
    private Object patdemographics;
    @SerializedName("patmedrecords")
    @Expose
    private Object patmedrecords;
    @SerializedName("patsubtenant")
    @Expose
    private Object patsubtenant;
    @SerializedName("rec_nature_id")
    @Expose
    private Integer recNatureId;
    @SerializedName("record_id")
    @Expose
    private Integer recordId;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("sub_tenant_id")
    @Expose
    private Object subTenantId;
    @SerializedName("user_role_id")
    @Expose
    private Integer userRoleId;

    public Integer getAgainstSubTenantId() {
        return againstSubTenantId;
    }

    public void setAgainstSubTenantId(Integer againstSubTenantId) {
        this.againstSubTenantId = againstSubTenantId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getESTBillno() {
        return eSTBillno;
    }

    public void setESTBillno(Integer eSTBillno) {
        this.eSTBillno = eSTBillno;
    }

    public Object getLabGrpTest() {
        return labGrpTest;
    }

    public void setLabGrpTest(Object labGrpTest) {
        this.labGrpTest = labGrpTest;
    }

    public Integer getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Integer labOrderId) {
        this.labOrderId = labOrderId;
    }

    public Object getLabPackageTest() {
        return labPackageTest;
    }

    public void setLabPackageTest(Object labPackageTest) {
        this.labPackageTest = labPackageTest;
    }

    public Integer getLabPharmacyType() {
        return labPharmacyType;
    }

    public void setLabPharmacyType(Integer labPharmacyType) {
        this.labPharmacyType = labPharmacyType;
    }

    public Object getLabTestList() {
        return labTestList;
    }

    public void setLabTestList(Object labTestList) {
        this.labTestList = labTestList;
    }

    public Integer getLaborderId() {
        return laborderId;
    }

    public void setLaborderId(Integer laborderId) {
        this.laborderId = laborderId;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public Integer getPatDemoid() {
        return patDemoid;
    }

    public void setPatDemoid(Integer patDemoid) {
        this.patDemoid = patDemoid;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getEntryTypeId() {
        return entryTypeId;
    }

    public void setEntryTypeId(Integer entryTypeId) {
        this.entryTypeId = entryTypeId;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Object getLaborders() {
        return laborders;
    }

    public void setLaborders(Object laborders) {
        this.laborders = laborders;
    }

    public Object getLaborderstatus() {
        return laborderstatus;
    }

    public void setLaborderstatus(Object laborderstatus) {
        this.laborderstatus = laborderstatus;
    }

    public Integer getOrderBySubTenantId() {
        return orderBySubTenantId;
    }

    public void setOrderBySubTenantId(Integer orderBySubTenantId) {
        this.orderBySubTenantId = orderBySubTenantId;
    }

    public Object getPatdemographics() {
        return patdemographics;
    }

    public void setPatdemographics(Object patdemographics) {
        this.patdemographics = patdemographics;
    }

    public Object getPatmedrecords() {
        return patmedrecords;
    }

    public void setPatmedrecords(Object patmedrecords) {
        this.patmedrecords = patmedrecords;
    }

    public Object getPatsubtenant() {
        return patsubtenant;
    }

    public void setPatsubtenant(Object patsubtenant) {
        this.patsubtenant = patsubtenant;
    }

    public Integer getRecNatureId() {
        return recNatureId;
    }

    public void setRecNatureId(Integer recNatureId) {
        this.recNatureId = recNatureId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Object getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Object subTenantId) {
        this.subTenantId = subTenantId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

}
