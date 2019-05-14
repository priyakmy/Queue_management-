package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mCURA1 on 8/18/2017.
 */

public class PatientInfoModel {

    public String umrNo;
    public String patName;
    public String mobileNo;
    public String patAge;
    public String emailId;
    public String gender;
    public String dob;
    public String regDate;

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    public String getUmrNo() {
        return umrNo;
    }

    public void setUmrNo(String umrNo) {
        this.umrNo = umrNo;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPatAge() {
        return patAge;
    }

    public void setPatAge(String patAge) {
        this.patAge = patAge;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


}
