
package com.mcura.jaideep.queuemanagement.Model.PatientVerificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("userRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("smsDelivered")
    @Expose
    private int smsDelivered;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getSmsDelivered() {
        return smsDelivered;
    }

    public void setSmsDelivered(int smsDelivered) {
        this.smsDelivered = smsDelivered;
    }
}
