package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mCURA1 on 3/10/2017.
 */

public class LabPharmacyOrderBoothModel {
    @SerializedName("Against_sub_tenant_id")
    @Expose
    private Integer Against_sub_tenant_id;

    @SerializedName("Amount")
    @Expose
    private Integer Amount;

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("Days")
    @Expose
    private int Days;

    @SerializedName("EST_Billno")
    @Expose
    private Integer EST_Billno;

    @SerializedName("LabPharmacyType")
    @Expose
    private Integer LabPharmacyType;

    @SerializedName("Mrno")
    @Expose
    private Integer Mrno;

    @SerializedName("Pat_demoid")
    @Expose
    private Integer Pat_demoid;

    @SerializedName("Patname")
    @Expose
    private String Patname;

    @SerializedName("Pres_order_id")
    @Expose
    private Integer Pres_order_id;

    @SerializedName("Laborder_id")
    @Expose
    private Integer Laborder_id;

    @SerializedName("LabOrderId")
    @Expose
    private Integer labOrderId;

    @SerializedName("address_id")
    @Expose
    private Integer address_id;

    @SerializedName("contact_id")
    @Expose
    private Integer contact_id;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("doctorName")
    @Expose
    private String doctorName;

    @SerializedName("entry_type_id")
    @Expose
    private Integer entry_type_id;

    @SerializedName("followup_id")
    @Expose
    private Integer followup_id;

    @SerializedName("gender_id")
    @Expose
    private Integer gender_id;

    @SerializedName("orderBy_sub_tenant_id")
    @Expose
    private Integer orderBy_sub_tenant_id;

    @SerializedName("prescription_id")
    @Expose
    private Integer prescription_id;

    @SerializedName("rec_nature_id")
    @Expose
    private Integer rec_nature_id;

    @SerializedName("record_id")
    @Expose
    private Integer record_id;

    @SerializedName("status_id")
    @Expose
    private Integer status_id;

    @SerializedName("user_role_id")
    @Expose
    private Integer user_role_id;

    public Integer getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Integer labOrderId) {
        this.labOrderId = labOrderId;
    }

    public Integer getAgainst_sub_tenant_id() {
        return Against_sub_tenant_id;
    }

    public void setAgainst_sub_tenant_id(Integer against_sub_tenant_id) {
        Against_sub_tenant_id = against_sub_tenant_id;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public Integer getEST_Billno() {
        return EST_Billno;
    }

    public void setEST_Billno(Integer EST_Billno) {
        this.EST_Billno = EST_Billno;
    }

    public Integer getLabPharmacyType() {
        return LabPharmacyType;
    }

    public void setLabPharmacyType(Integer labPharmacyType) {
        LabPharmacyType = labPharmacyType;
    }

    public Integer getMrno() {
        return Mrno;
    }

    public void setMrno(Integer mrno) {
        Mrno = mrno;
    }

    public Integer getPat_demoid() {
        return Pat_demoid;
    }

    public void setPat_demoid(Integer pat_demoid) {
        Pat_demoid = pat_demoid;
    }

    public String getPatname() {
        return Patname;
    }

    public void setPatname(String patname) {
        Patname = patname;
    }

    public Integer getPres_order_id() {
        return Pres_order_id;
    }

    public void setPres_order_id(Integer pres_order_id) {
        Pres_order_id = pres_order_id;
    }

    public Integer getLaborder_id() {
        return Laborder_id;
    }

    public void setLaborder_id(Integer laborder_id) {
        Laborder_id = laborder_id;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
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

    public Integer getEntry_type_id() {
        return entry_type_id;
    }

    public void setEntry_type_id(Integer entry_type_id) {
        this.entry_type_id = entry_type_id;
    }

    public Integer getFollowup_id() {
        return followup_id;
    }

    public void setFollowup_id(Integer followup_id) {
        this.followup_id = followup_id;
    }

    public Integer getGender_id() {
        return gender_id;
    }

    public void setGender_id(Integer gender_id) {
        this.gender_id = gender_id;
    }

    public Integer getOrderBy_sub_tenant_id() {
        return orderBy_sub_tenant_id;
    }

    public void setOrderBy_sub_tenant_id(Integer orderBy_sub_tenant_id) {
        this.orderBy_sub_tenant_id = orderBy_sub_tenant_id;
    }

    public Integer getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(Integer prescription_id) {
        this.prescription_id = prescription_id;
    }

    public Integer getRec_nature_id() {
        return rec_nature_id;
    }

    public void setRec_nature_id(Integer rec_nature_id) {
        this.rec_nature_id = rec_nature_id;
    }

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(Integer user_role_id) {
        this.user_role_id = user_role_id;
    }
}
