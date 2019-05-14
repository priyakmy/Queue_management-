
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPatientByHospitalNoModel {

    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("duplicaltePrintDate")
    @Expose
    private String duplicaltePrintDate;
    @SerializedName("duplicatePrintedBy")
    @Expose
    private String duplicatePrintedBy;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("hospitalNo")
    @Expose
    private String hospitalNo;
    @SerializedName("maritusStatusId")
    @Expose
    private Integer maritusStatusId;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("patName")
    @Expose
    private String patName;
    @SerializedName("printDate")
    @Expose
    private String printDate;
    @SerializedName("printedBy")
    @Expose
    private String printedBy;
    @SerializedName("regDate")
    @Expose
    private String regDate;

    public String getDuplicaltePrintDate() {
        return duplicaltePrintDate;
    }

    public void setDuplicaltePrintDate(String duplicaltePrintDate) {
        this.duplicaltePrintDate = duplicaltePrintDate;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDuplicatePrintedBy() {
        return duplicatePrintedBy;
    }

    public void setDuplicatePrintedBy(String duplicatePrintedBy) {
        this.duplicatePrintedBy = duplicatePrintedBy;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public Integer getMaritusStatusId() {
        return maritusStatusId;
    }

    public void setMaritusStatusId(Integer maritusStatusId) {
        this.maritusStatusId = maritusStatusId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getPrintedBy() {
        return printedBy;
    }

    public void setPrintedBy(String printedBy) {
        this.printedBy = printedBy;
    }
}
