
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeFetch {

    @SerializedName("FeeAmount")
    @Expose
    private Integer feeAmount;
    @SerializedName("UserRoleId")
    @Expose
    private Integer userRoleId;

    /**
     * 
     * @return
     *     The feeAmount
     */
    public Integer getFeeAmount() {
        return feeAmount;
    }

    /**
     * 
     * @param feeAmount
     *     The FeeAmount
     */
    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
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

}
