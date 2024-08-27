package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeData {

    @SerializedName("natureId")
    @Expose
    private Integer natureId;
    @SerializedName("nature")
    @Expose
    private String nature;
    @SerializedName("turnAroundTime")
    @Expose
    private String turnAroundTime;
    @SerializedName("fee")
    @Expose
    private Integer fee;
    @SerializedName("partnerFee")
    @Expose
    private Double partnerFee;
    @SerializedName("txnFee")
    @Expose
    private Double txnFee;
    @SerializedName("userRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("discountPercentage")
    @Expose
    private Integer discountPercentage;
    @SerializedName("discountAmount")
    @Expose
    private Integer discountAmount;

    public Integer getNatureId() {
        return natureId;
    }

    public void setNatureId(Integer natureId) {
        this.natureId = natureId;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(String turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Double getPartnerFee() {
        return partnerFee;
    }

    public void setPartnerFee(Double partnerFee) {
        this.partnerFee = partnerFee;
    }

    public Double getTxnFee() {
        return txnFee;
    }

    public void setTxnFee(Double txnFee) {
        this.txnFee = txnFee;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

}