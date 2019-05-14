
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNatureByUserRoleModel {

    @SerializedName("AppNature")
    @Expose
    private String appNature;
    @SerializedName("AppNatureID")
    @Expose
    private Integer appNatureID;
    @SerializedName("FeeAmount")
    @Expose
    private Object feeAmount;
    @SerializedName("UserRoleId")
    @Expose
    private Object userRoleId;

    /**
     * 
     * @return
     *     The appNature
     */
    public String getAppNature() {
        return appNature;
    }

    /**
     * 
     * @param appNature
     *     The AppNature
     */
    public void setAppNature(String appNature) {
        this.appNature = appNature;
    }

    /**
     * 
     * @return
     *     The appNatureID
     */
    public Integer getAppNatureID() {
        return appNatureID;
    }

    /**
     * 
     * @param appNatureID
     *     The AppNatureID
     */
    public void setAppNatureID(Integer appNatureID) {
        this.appNatureID = appNatureID;
    }

    /**
     * 
     * @return
     *     The feeAmount
     */
    public Object getFeeAmount() {
        return feeAmount;
    }

    /**
     * 
     * @param feeAmount
     *     The FeeAmount
     */
    public void setFeeAmount(Object feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * 
     * @return
     *     The userRoleId
     */
    public Object getUserRoleId() {
        return userRoleId;
    }

    /**
     * 
     * @param userRoleId
     *     The UserRoleId
     */
    public void setUserRoleId(Object userRoleId) {
        this.userRoleId = userRoleId;
    }

}
