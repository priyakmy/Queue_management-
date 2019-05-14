package com.mcura.jaideep.queuemanagement.Model.LabOrderTransactionDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabTxnDetailModel {
    private int txnId;
    private String invName;
    private double invAmount;

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public double getInvAmount() {
        return invAmount;
    }

    public void setInvAmount(double invAmount) {
        this.invAmount = invAmount;
    }
}
