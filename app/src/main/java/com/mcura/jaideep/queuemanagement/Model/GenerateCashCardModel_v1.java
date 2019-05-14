package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mCURA1 on 3/13/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GenerateCashCardModel_v1 {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("filepath")
    @Expose
    private Object filepath;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public Object getFilepath() {
        return filepath;
    }

    public void setFilepath(Object filepath) {
        this.filepath = filepath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
