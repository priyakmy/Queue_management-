package com.mcura.jaideep.queuemanagement.Model.LabOrderTransactionDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderTransactionDetail.TxnDetail;

import java.util.List;

public class LabOrderTransactionModel {
    @SerializedName("orderedDate")
    @Expose
    private String orderedDate;
    @SerializedName("ordTxnId")
    @Expose
    private Integer ordTxnId;
    @SerializedName("ordStatus")
    @Expose
    private Integer ordStatus;
    @SerializedName("orderedAmount")
    @Expose
    private double orderedAmount;
    @SerializedName("billId")
    @Expose
    private Integer billId;
    @SerializedName("billDate")
    @Expose
    private String billDate;
    @SerializedName("pymtNature")
    @Expose
    private String pymtNature;
    @SerializedName("HISBillNo")
    @Expose
    private String hISBillNo;
    @SerializedName("appNatureId")
    @Expose
    private Integer appNatureId;
    @SerializedName("prescriptionId")
    @Expose
    private Integer prescriptionId;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("appotId")
    @Expose
    private Integer appotId;
    @SerializedName("labtxnDetails")
    @Expose
    private List<LabTxnDetailModel> labTxnDetailModelList = null;

    private boolean status;

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Integer getOrdTxnId() {
        return ordTxnId;
    }

    public void setOrdTxnId(Integer ordTxnId) {
        this.ordTxnId = ordTxnId;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public double getOrderedAmount() {
        return orderedAmount;
    }

    public void setOrderedAmount(double orderedAmount) {
        this.orderedAmount = orderedAmount;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPymtNature() {
        return pymtNature;
    }

    public void setPymtNature(String pymtNature) {
        this.pymtNature = pymtNature;
    }

    public String gethISBillNo() {
        return hISBillNo;
    }

    public void sethISBillNo(String hISBillNo) {
        this.hISBillNo = hISBillNo;
    }

    public Integer getAppNatureId() {
        return appNatureId;
    }

    public void setAppNatureId(Integer appNatureId) {
        this.appNatureId = appNatureId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getAppotId() {
        return appotId;
    }

    public void setAppotId(Integer appotId) {
        this.appotId = appotId;
    }

    public List<LabTxnDetailModel> getLabTxnDetailModelList() {
        return labTxnDetailModelList;
    }

    public void setLabTxnDetailModelList(List<LabTxnDetailModel> labTxnDetailModelList) {
        this.labTxnDetailModelList = labTxnDetailModelList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
