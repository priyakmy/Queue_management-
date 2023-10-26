package com.mcura.jaideep.queuemanagement.Utils;

public enum NatureEnum {
    mLabReport(2),mCurrentVisitImage(6),mVisitSummary(13);
    private int natureId;

    NatureEnum(int natureId) {
        this.natureId = natureId;
    }

    public int natureId() {
        return natureId;
    }
}
