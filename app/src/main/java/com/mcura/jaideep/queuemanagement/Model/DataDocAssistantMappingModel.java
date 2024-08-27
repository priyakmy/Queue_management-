package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataDocAssistantMappingModel {


    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("serviceRoleId")
    @Expose
    private Integer serviceRoleId;
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("deptId")
    @Expose
    private Integer deptId;
    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;
    @SerializedName("docFee")
    @Expose
    private String docFee;
    @SerializedName("docExperience")
    @Expose
    private String docExperience;
    @SerializedName("docEducation")
    @Expose
    private String docEducation;
    @SerializedName("mobilePhoto")
    @Expose
    private String mobilePhoto;
    @SerializedName("ipadPhoto")
    @Expose
    private String ipadPhoto;
    @SerializedName("coverPhoto")
    @Expose
    private String coverPhoto;
    @SerializedName("profilephoto")
    @Expose
    private String profilephoto;
    @SerializedName("docProfilePath")
    @Expose
    private String docProfilePath;
    @SerializedName("deptProfilePath")
    @Expose
    private String deptProfilePath;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getServiceRoleId() {
        return serviceRoleId;
    }

    public void setServiceRoleId(Integer serviceRoleId) {
        this.serviceRoleId = serviceRoleId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getDocFee() {
        return docFee;
    }

    public void setDocFee(String docFee) {
        this.docFee = docFee;
    }

    public String getDocExperience() {
        return docExperience;
    }

    public void setDocExperience(String docExperience) {
        this.docExperience = docExperience;
    }

    public String getDocEducation() {
        return docEducation;
    }

    public void setDocEducation(String docEducation) {
        this.docEducation = docEducation;
    }

    public String getMobilePhoto() {
        return mobilePhoto;
    }

    public void setMobilePhoto(String mobilePhoto) {
        this.mobilePhoto = mobilePhoto;
    }

    public String getIpadPhoto() {
        return ipadPhoto;
    }

    public void setIpadPhoto(String ipadPhoto) {
        this.ipadPhoto = ipadPhoto;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getDocProfilePath() {
        return docProfilePath;
    }

    public void setDocProfilePath(String docProfilePath) {
        this.docProfilePath = docProfilePath;
    }

    public String getDeptProfilePath() {
        return deptProfilePath;
    }

    public void setDeptProfilePath(String deptProfilePath) {
        this.deptProfilePath = deptProfilePath;
    }

}
