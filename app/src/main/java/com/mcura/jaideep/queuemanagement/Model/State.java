
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("CntyId")
    @Expose
    private Integer CntyId;
    @SerializedName("StateId")
    @Expose
    private Integer StateId;
    @SerializedName("StateProperty")
    @Expose
    private String StateProperty;

    /**
     * 
     * @return
     *     The CntyId
     */
    public Integer getCntyId() {
        return CntyId;
    }

    /**
     * 
     * @param CntyId
     *     The CntyId
     */
    public void setCntyId(Integer CntyId) {
        this.CntyId = CntyId;
    }

    /**
     * 
     * @return
     *     The StateId
     */
    public Integer getStateId() {
        return StateId;
    }

    /**
     * 
     * @param StateId
     *     The StateId
     */
    public void setStateId(Integer StateId) {
        this.StateId = StateId;
    }

    /**
     * 
     * @return
     *     The StateProperty
     */
    public String getStateProperty() {
        return StateProperty;
    }

    /**
     * 
     * @param StateProperty
     *     The StateProperty
     */
    public void setStateProperty(String StateProperty) {
        this.StateProperty = StateProperty;
    }

}
