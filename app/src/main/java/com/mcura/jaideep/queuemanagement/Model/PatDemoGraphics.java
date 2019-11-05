package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mCURA1 on 2/23/2016.
 */
public class PatDemoGraphics implements Serializable{
    @SerializedName("AddressId")
    @Expose
    private int AddressId;
    @SerializedName("ContactId")
    @Expose
    private int ContactId;
    @SerializedName("Dob")
    @Expose
    private String Dob;
    @SerializedName("GenderId")
    @Expose
    private int GenderId;
    @SerializedName("PatDemoid")
    @Expose
    private int PatDemoid;
    @SerializedName("Patname")
    @Expose
    private String Patname;
    @SerializedName("HospitalId")
    @Expose
    private String hospitalId;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Mobile")
    @Expose
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public int getContactId() {
        return ContactId;
    }

    public void setContactId(int contactId) {
        ContactId = contactId;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }

    public String getPatname() {
        return Patname;
    }

    public void setPatname(String patname) {
        Patname = patname;
    }

    public int getPatDemoid() {
        return PatDemoid;
    }

    public void setPatDemoid(int patDemoid) {
        PatDemoid = patDemoid;
    }
}
