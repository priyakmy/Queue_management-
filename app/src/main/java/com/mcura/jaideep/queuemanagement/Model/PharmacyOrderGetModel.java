
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyOrderGetModel {

    @SerializedName("Against_sub_tenant_id")
    @Expose
    private Integer againstSubTenantId;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("ConnectionString")
    @Expose
    private Object connectionString;
    @SerializedName("Date")
    @Expose
    private Object date;
    @SerializedName("Days")
    @Expose
    private Integer days;
    @SerializedName("EST_Billno")
    @Expose
    private Integer eSTBillno;
    @SerializedName("LabPharmacyType")
    @Expose
    private Integer labPharmacyType;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("Pat_demoid")
    @Expose
    private Object patDemoid;
    @SerializedName("Patname")
    @Expose
    private Object patname;
    @SerializedName("Pres_order_id")
    @Expose
    private Integer presOrderId;
    @SerializedName("Total")
    @Expose
    private Object total;
    @SerializedName("address_id")
    @Expose
    private Object addressId;
    @SerializedName("contact_id")
    @Expose
    private Object contactId;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("doctorName")
    @Expose
    private Object doctorName;
    @SerializedName("entry_type_id")
    @Expose
    private Object entryTypeId;
    @SerializedName("followup_id")
    @Expose
    private Object followupId;
    @SerializedName("gender_id")
    @Expose
    private Object genderId;
    @SerializedName("orderBy_sub_tenant_id")
    @Expose
    private Integer orderBySubTenantId;
    @SerializedName("prescription_id")
    @Expose
    private Integer prescriptionId;
    @SerializedName("rec_nature_id")
    @Expose
    private Object recNatureId;
    @SerializedName("record_id")
    @Expose
    private Object recordId;
    @SerializedName("status_id")
    @Expose
    private Object statusId;
    @SerializedName("sub_tenant_id")
    @Expose
    private Integer subTenantId;
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

    public Object getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(Object connectionString) {
        this.connectionString = connectionString;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
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

    public Integer getLabPharmacyType() {
        return labPharmacyType;
    }

    public void setLabPharmacyType(Integer labPharmacyType) {
        this.labPharmacyType = labPharmacyType;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public Object getPatDemoid() {
        return patDemoid;
    }

    public void setPatDemoid(Object patDemoid) {
        this.patDemoid = patDemoid;
    }

    public Object getPatname() {
        return patname;
    }

    public void setPatname(Object patname) {
        this.patname = patname;
    }

    public Integer getPresOrderId() {
        return presOrderId;
    }

    public void setPresOrderId(Integer presOrderId) {
        this.presOrderId = presOrderId;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
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

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(Object doctorName) {
        this.doctorName = doctorName;
    }

    public Object getEntryTypeId() {
        return entryTypeId;
    }

    public void setEntryTypeId(Object entryTypeId) {
        this.entryTypeId = entryTypeId;
    }

    public Object getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Object followupId) {
        this.followupId = followupId;
    }

    public Object getGenderId() {
        return genderId;
    }

    public void setGenderId(Object genderId) {
        this.genderId = genderId;
    }

    public Integer getOrderBySubTenantId() {
        return orderBySubTenantId;
    }

    public void setOrderBySubTenantId(Integer orderBySubTenantId) {
        this.orderBySubTenantId = orderBySubTenantId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Object getRecNatureId() {
        return recNatureId;
    }

    public void setRecNatureId(Object recNatureId) {
        this.recNatureId = recNatureId;
    }

    public Object getRecordId() {
        return recordId;
    }

    public void setRecordId(Object recordId) {
        this.recordId = recordId;
    }

    public Object getStatusId() {
        return statusId;
    }

    public void setStatusId(Object statusId) {
        this.statusId = statusId;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

}
