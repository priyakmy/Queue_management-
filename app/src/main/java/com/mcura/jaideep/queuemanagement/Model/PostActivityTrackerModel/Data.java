
package com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ActivityKey")
    @Expose
    private Integer activityKey;

    public Integer getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(Integer activityKey) {
        this.activityKey = activityKey;
    }

}
