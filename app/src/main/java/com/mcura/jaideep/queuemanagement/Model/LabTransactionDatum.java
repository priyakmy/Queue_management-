
package com.mcura.jaideep.queuemanagement.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabTransactionDatum {

    @SerializedName("docName")
    @Expose
    private String docName;
    @SerializedName("serviceType")
    @Expose
    private Integer serviceType;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("userRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("subtenantId")
    @Expose
    private Integer subtenantId;
    @SerializedName("labSubtenant")
    @Expose
    private Integer labSubtenant;
    @SerializedName("estmAmount")
    @Expose
    private Integer estmAmount;
    @SerializedName("cashCardId")
    @Expose
    private Integer cashCardId;
    @SerializedName("scheduleId")
    @Expose
    private Integer scheduleId;
    @SerializedName("prescribedDate")
    @Expose
    private String prescribedDate;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getSubtenantId() {
        return subtenantId;
    }

    public void setSubtenantId(Integer subtenantId) {
        this.subtenantId = subtenantId;
    }

    public Integer getLabSubtenant() {
        return labSubtenant;
    }

    public void setLabSubtenant(Integer labSubtenant) {
        this.labSubtenant = labSubtenant;
    }

    public Integer getEstmAmount() {
        return estmAmount;
    }

    public void setEstmAmount(Integer estmAmount) {
        this.estmAmount = estmAmount;
    }

    public Integer getCashCardId() {
        return cashCardId;
    }

    public void setCashCardId(Integer cashCardId) {
        this.cashCardId = cashCardId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getPrescribedDate() {
        return prescribedDate;
    }

    public void setPrescribedDate(String prescribedDate) {
        this.prescribedDate = prescribedDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
