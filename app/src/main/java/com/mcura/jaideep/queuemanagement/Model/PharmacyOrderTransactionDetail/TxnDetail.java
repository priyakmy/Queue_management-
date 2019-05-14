
package com.mcura.jaideep.queuemanagement.Model.PharmacyOrderTransactionDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TxnDetail {

    @SerializedName("txnId")
    @Expose
    private Integer txnId;
    @SerializedName("medName")
    @Expose
    private String medName;
    @SerializedName("medAmount")
    @Expose
    private double medAmount;

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public double getMedAmount() {
        return medAmount;
    }

    public void setMedAmount(double medAmount) {
        this.medAmount = medAmount;
    }

}
