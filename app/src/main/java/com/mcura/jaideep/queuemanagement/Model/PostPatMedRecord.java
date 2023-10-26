package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPatMedRecord {
    @SerializedName("Ids")
    @Expose
    private List<Integer> ids = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("RowsAdded")
    @Expose
    private String rowsAdded;

    /**
     *
     * @return
     *     The status
     */

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

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
     *     The rowsAdded
     */
    public String getRowsAdded() {
        return rowsAdded;
    }

    /**
     *
     * @param rowsAdded
     *     The RowsAdded
     */
    public void setRowsAdded(String rowsAdded) {
        this.rowsAdded = rowsAdded;
    }

}