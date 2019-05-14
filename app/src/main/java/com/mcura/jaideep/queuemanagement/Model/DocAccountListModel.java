
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocAccountListModel {

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
    private Integer paymentStatus;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("billId")
    @Expose
    private Integer billId;
    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("patName")
    @Expose
    private String patName;
    @SerializedName("picPath")
    @Expose
    private String picPath;
    @SerializedName("pymtNature")
    @Expose
    private String pymtNature;
    @SerializedName("HospitalNo")
    @Expose
    private String HospitalNo;
    public String getHospitalNo() {
        return HospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        HospitalNo = hospitalNo;
    }
    /**
     * 
     * @return
     *     The amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The Amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The billDate
     */
    public String getBillDate() {
        return billDate;
    }

    /**
     * 
     * @param billDate
     *     The BillDate
     */
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * @return
     *     The docName
     */
    public String getDocName() {
        return docName;
    }

    /**
     * 
     * @param docName
     *     The DocName
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * 
     * @return
     *     The mrno
     */
    public Integer getMrno() {
        return mrno;
    }

    /**
     * 
     * @param mrno
     *     The Mrno
     */
    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    /**
     * 
     * @return
     *     The paymentStatus
     */
    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * 
     * @param paymentStatus
     *     The Payment_Status
     */
    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * 
     * @return
     *     The age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 
     * @param age
     *     The age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 
     * @return
     *     The billId
     */
    public Integer getBillId() {
        return billId;
    }

    /**
     * 
     * @param billId
     *     The billId
     */
    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    /**
     * 
     * @return
     *     The genderId
     */
    public Integer getGenderId() {
        return genderId;
    }

    /**
     * 
     * @param genderId
     *     The genderId
     */
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    /**
     * 
     * @return
     *     The patName
     */
    public String getPatName() {
        return patName;
    }

    /**
     * 
     * @param patName
     *     The patName
     */
    public void setPatName(String patName) {
        this.patName = patName;
    }

    /**
     * 
     * @return
     *     The picPath
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * 
     * @param picPath
     *     The picPath
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * 
     * @return
     *     The pymtNature
     */
    public String getPymtNature() {
        return pymtNature;
    }

    /**
     * 
     * @param pymtNature
     *     The pymtNature
     */
    public void setPymtNature(String pymtNature) {
        this.pymtNature = pymtNature;
    }

}
