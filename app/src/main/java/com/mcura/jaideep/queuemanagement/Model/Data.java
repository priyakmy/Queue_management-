
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mcura.jaideep.queuemanagement.Activity.TransactionSummary;

import java.util.List;

public class Data {
    @SerializedName("transactions")
    @Expose
    private List<TransactionSummaryModel> transactions = null;

    public List<TransactionSummaryModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionSummaryModel> transactions) {
        this.transactions = transactions;
    }

}
