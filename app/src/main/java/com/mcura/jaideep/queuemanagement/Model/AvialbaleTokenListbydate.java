
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvialbaleTokenListbydate {
    @SerializedName("Dept")
    @Expose
    private String Dept;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Schedule")
    @Expose
    private Schedule schedule;
    @SerializedName("UserRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("serviceRoleId")
    @Expose
    private int serviceRoleId;

    public int getServiceRoleId() {
        return serviceRoleId;
    }

    public void setServiceRoleId(int serviceRoleId) {
        this.serviceRoleId = serviceRoleId;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    /**
     * 
     * @return
     *     The doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * 
     * @param doctorName
     *     The DoctorName
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The Image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * 
     * @param schedule
     *     The Schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * 
     * @return
     *     The userRoleId
     */
    public Integer getUserRoleId() {
        return userRoleId;
    }

    /**
     * 
     * @param userRoleId
     *     The UserRoleId
     */
    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
