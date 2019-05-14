
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckHospitalPatModel {

    @SerializedName("RowsAdded")
    @Expose
    private Object rowsAdded;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     * 
     * @return
     *     The rowsAdded
     */
    public Object getRowsAdded() {
        return rowsAdded;
    }

    /**
     * 
     * @param rowsAdded
     *     The RowsAdded
     */
    public void setRowsAdded(Object rowsAdded) {
        this.rowsAdded = rowsAdded;
    }

    /**
     * 
     * @return
     *     The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *     The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

}
