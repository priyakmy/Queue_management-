package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionSummaryModel {
    @SerializedName("Patname")
    @Expose
    private String Patname;

    @SerializedName("hospitalNo")
    @Expose
    private String hospitalNo;

    @SerializedName("Age")
    @Expose
    private String Age;

    @SerializedName("genderId")
    @Expose
    private int genderId;

    @SerializedName("txnId")
    @Expose
    private String txnId;

    @SerializedName("billNo")
    @Expose
    private String billNo;

    @SerializedName("Payment_Mode")
    @Expose
    private String Payment_Mode;

    @SerializedName("cashCardId")
    @Expose
    private String cashCardId;

    @SerializedName("txnAmount")
    @Expose
    private double txnAmount;

    @SerializedName("txnDate")
    @Expose
    private String txnDate;

    @SerializedName("uname")
    @Expose
    private String uname;

    @SerializedName("mrNo")
    @Expose
    private String mrNo;

    @SerializedName("service_id")
    @Expose
    private String service_id;

    @SerializedName("serviceTypeId")
    @Expose
    private String serviceTypeId;

    @SerializedName("payment_status")
    @Expose
    private String payment_status;

    @SerializedName("txnStatus")
    @Expose
    private String txnStatus;

    @SerializedName("txnDesc")
    @Expose
    private String txnDesc;

    @SerializedName("Credit")
    @Expose
    private String Credit;

    @SerializedName("deptName")
    @Expose
    private String deptName;

    @SerializedName("billDiscount")
    @Expose
    private List<BillDiscount> billDiscount = null;

    public String getPatname() {
        return Patname;
    }

    public void setPatname(String patname) {
        Patname = patname;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getPayment_Mode() {
        return Payment_Mode;
    }

    public void setPayment_Mode(String payment_Mode) {
        Payment_Mode = payment_Mode;
    }

    public String getCashCardId() {
        return cashCardId;
    }

    public void setCashCardId(String cashCardId) {
        this.cashCardId = cashCardId;
    }

    public double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getTxnDesc() {
        return txnDesc;
    }

    public void setTxnDesc(String txnDesc) {
        this.txnDesc = txnDesc;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<BillDiscount> getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(List<BillDiscount> billDiscount) {
        this.billDiscount = billDiscount;
    }
}
