
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorListModel {

    @SerializedName("UserId")
    @Expose
    private Integer UserId;
    @SerializedName("UserName")
    @Expose
    private String UserName;
    @SerializedName("UserRoleId")
    @Expose
    private Integer UserRoleId;
    @SerializedName("serviceRoleId")
    @Expose
    private int serviceRoleId;

    @SerializedName("deptId")
    @Expose
    private int deptId;

    @SerializedName("deptName")
    @Expose
    private String deptName;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getServiceRoleId() {
        return serviceRoleId;
    }

    public void setServiceRoleId(int serviceRoleId) {
        this.serviceRoleId = serviceRoleId;
    }

    /**
     * 
     * @return
     *     The UserId
     */
    public Integer getUserId() {
        return UserId;
    }

    /**
     * 
     * @param UserId
     *     The UserId
     */
    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    /**
     * 
     * @return
     *     The UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * 
     * @param UserName
     *     The UserName
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * 
     * @return
     *     The UserRoleId
     */
    public Integer getUserRoleId() {
        return UserRoleId;
    }

    /**
     * 
     * @param UserRoleId
     *     The UserRoleId
     */
    public void setUserRoleId(Integer UserRoleId) {
        this.UserRoleId = UserRoleId;
    }

}
