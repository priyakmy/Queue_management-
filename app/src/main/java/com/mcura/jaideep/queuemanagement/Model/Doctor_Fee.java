package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mCURA1 on 10/4/2016.
 */
public class Doctor_Fee {
    private String Fee_id;
    private String User_Role_id;
    private String App_Nature_id;
    private String Fee_Amount;

    public String getFee_id() {
        return Fee_id;
    }

    public void setFee_id(String fee_id) {
        Fee_id = fee_id;
    }

    public String getUser_Role_id() {
        return User_Role_id;
    }

    public void setUser_Role_id(String user_Role_id) {
        User_Role_id = user_Role_id;
    }

    public String getApp_Nature_id() {
        return App_Nature_id;
    }

    public void setApp_Nature_id(String app_Nature_id) {
        App_Nature_id = app_Nature_id;
    }

    public String getFee_Amount() {
        return Fee_Amount;
    }

    public void setFee_Amount(String fee_Amount) {
        Fee_Amount = fee_Amount;
    }
}
