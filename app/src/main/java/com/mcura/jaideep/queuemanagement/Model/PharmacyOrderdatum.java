
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyOrderdatum {

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
    private String date;
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
    private Integer patDemoid;
    @SerializedName("Patname")
    @Expose
    private String patname;
    @SerializedName("Pres_order_id")
    @Expose
    private Integer presOrderId;
    @SerializedName("Total")
    @Expose
    private Object total;
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
    @SerializedName("followup_id")
    @Expose
    private Integer followupId;
    @SerializedName("gender_id")
    @Expose
    private Integer genderId;
    @SerializedName("orderBy_sub_tenant_id")
    @Expose
    private Integer orderBySubTenantId;
    @SerializedName("prescription_id")
    @Expose
    private Integer prescriptionId;
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

    public Object getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(Object connectionString) {
        this.connectionString = connectionString;
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

    public Integer getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Integer followupId) {
        this.followupId = followupId;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
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
