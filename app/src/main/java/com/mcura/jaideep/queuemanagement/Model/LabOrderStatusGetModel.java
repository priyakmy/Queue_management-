
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabOrderStatusGetModel {

    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Date")
    @Expose
    private Object date;
    @SerializedName("Days")
    @Expose
    private Integer days;
    @SerializedName("EST_Billno")
    @Expose
    private Integer eSTBillno;
    @SerializedName("LabOrderId")
    @Expose
    private Integer labOrderId;
    @SerializedName("Laborder_id")
    @Expose
    private Integer laborderId;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("StatusId")
    @Expose
    private Object statusId;
    @SerializedName("SubTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("UserRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Integer getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Integer labOrderId) {
        this.labOrderId = labOrderId;
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
