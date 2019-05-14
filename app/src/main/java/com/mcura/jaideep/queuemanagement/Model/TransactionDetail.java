
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionDetail {

    @SerializedName("data")
    @Expose
    private List<TransactionSummaryModel> transactions = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<TransactionSummaryModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionSummaryModel> transactions) {
        this.transactions = transactions;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
