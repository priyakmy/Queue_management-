package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mCURA1 on 3/10/2017.
 */

public class SearchCashCardModel {
    @SerializedName("CashCard_ID")
    @Expose
    private int cashCardID;
    @SerializedName("Mrno")
    @Expose
    private String mrno;

    public int getCashCardID() {
        return cashCardID;
    }

    public void setCashCardID(int cashCardID) {
        this.cashCardID = cashCardID;
    }

    public String getMrno() {
        return mrno;
    }

    public void setMrno(String mrno) {
        this.mrno = mrno;
    }
}
