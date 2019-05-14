package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedantaPatInfo {
    @SerializedName("PatientUHID")
    @Expose
    private String patientUHID;
    @SerializedName("PatientName")
    @Expose
    private String patientName;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ResidentialArea")
    @Expose
    private String residentialArea;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("PostalCode")
    @Expose
    private String postalCode;
    @SerializedName("Country")
    @Expose
    private String Country;

    public String getPatientUHID() {
        return patientUHID;
    }

    public void setPatientUHID(String patientUHID) {
        this.patientUHID = patientUHID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResidentialArea() {
        return residentialArea;
    }

    public void setResidentialArea(String residentialArea) {
        this.residentialArea = residentialArea;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
