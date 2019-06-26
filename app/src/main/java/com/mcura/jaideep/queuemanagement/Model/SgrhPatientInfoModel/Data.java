
package com.mcura.jaideep.queuemanagement.Model.SgrhPatientInfoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("PatientNo")
    @Expose
    private Integer patientNo;
    @SerializedName("PatientName")
    @Expose
    private String patientName;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("FatherName")
    @Expose
    private String fatherName;
    @SerializedName("MaritusStatus")
    @Expose
    private String maritusStatus;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("RegDate")
    @Expose
    private String regDate;
    @SerializedName("Email")
    @Expose
    private String email;

    public Integer getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(Integer patientNo) {
        this.patientNo = patientNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMaritusStatus() {
        return maritusStatus;
    }

    public void setMaritusStatus(String maritusStatus) {
        this.maritusStatus = maritusStatus;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
