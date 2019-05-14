
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastBillDetailModel {

    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("BillDate")
    @Expose
    private String billDate;
    @SerializedName("DocName")
    @Expose
    private String docName;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("Payment_Status")
    @Expose
    private Object paymentStatus;
    @SerializedName("age")
    @Expose
    private Object age;
    @SerializedName("billId")
    @Expose
    private Integer billId;
    @SerializedName("genderId")
    @Expose
    private Object genderId;
    @SerializedName("patName")
    @Expose
    private Object patName;
    @SerializedName("picPath")
    @Expose
    private Object picPath;
    @SerializedName("pymtNature")
    @Expose
    private String pymtNature;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public Object getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Object getGenderId() {
        return genderId;
    }

    public void setGenderId(Object genderId) {
        this.genderId = genderId;
    }

    public Object getPatName() {
        return patName;
    }

    public void setPatName(Object patName) {
        this.patName = patName;
    }

    public Object getPicPath() {
        return picPath;
    }

    public void setPicPath(Object picPath) {
        this.picPath = picPath;
    }

    public String getPymtNature() {
        return pymtNature;
    }

    public void setPymtNature(String pymtNature) {
        this.pymtNature = pymtNature;
    }
}
