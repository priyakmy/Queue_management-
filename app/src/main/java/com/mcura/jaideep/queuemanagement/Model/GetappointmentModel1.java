
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetappointmentModel1 {

    @SerializedName("appointments")
    @Expose
    private List<AppointmentModel> appointments = null;
    @SerializedName("scheduleId")
    @Expose
    private Integer scheduleId;
    @SerializedName("deptId")
    @Expose
    private Integer deptId;
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("serviceType")
    @Expose
    private ServiceType serviceType;
    @SerializedName("subTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userImg")
    @Expose
    private String userImg;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userRolelId")
    @Expose
    private Integer userRolelId;

    public List<AppointmentModel> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentModel> appointments) {
        this.appointments = appointments;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserRolelId() {
        return userRolelId;
    }

    public void setUserRolelId(Integer userRolelId) {
        this.userRolelId = userRolelId;
    }

}
