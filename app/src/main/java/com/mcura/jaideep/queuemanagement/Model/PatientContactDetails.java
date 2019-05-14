package com.mcura.jaideep.queuemanagement.Model;

/**
 * Created by mcura on 11/6/2015.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientContactDetails implements Serializable {

    @SerializedName("ContactId")
    @Expose
    private Integer ContactId;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("FixLine")
    @Expose
    private String FixLine;
    @SerializedName("FixLineExtn")
    @Expose
    private String FixLineExtn;
    @SerializedName("Mobile")
    @Expose
    private String Mobile;
    @SerializedName("Optemail")
    @Expose
    private String Optemail;
    @SerializedName("Optmobile")
    @Expose
    private String Optmobile;
    @SerializedName("Skypeid")
    @Expose
    private String Skypeid;

    /**
     *
     * @return
     * The ContactId
     */
    public Integer getContactId() {
        return ContactId;
    }

    /**
     *
     * @param ContactId
     * The ContactId
     */
    public void setContactId(Integer ContactId) {
        this.ContactId = ContactId;
    }

    /**
     *
     * @return
     * The Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     *
     * @param Email
     * The Email
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     *
     * @return
     * The FixLine
     */
    public String getFixLine() {
        return FixLine;
    }

    /**
     *
     * @param FixLine
     * The FixLine
     */
    public void setFixLine(String FixLine) {
        this.FixLine = FixLine;
    }

    /**
     *
     * @return
     * The FixLineExtn
     */
    public String getFixLineExtn() {
        return FixLineExtn;
    }

    /**
     *
     * @param FixLineExtn
     * The FixLineExtn
     */
    public void setFixLineExtn(String FixLineExtn) {
        this.FixLineExtn = FixLineExtn;
    }

    /**
     *
     * @return
     * The Mobile
     */
    public String getMobile() {
        return Mobile;
    }

    /**
     *
     * @param Mobile
     * The Mobile
     */
    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    /**
     *
     * @return
     * The Optemail
     */
    public String getOptemail() {
        return Optemail;
    }

    /**
     *
     * @param Optemail
     * The Optemail
     */
    public void setOptemail(String Optemail) {
        this.Optemail = Optemail;
    }

    /**
     *
     * @return
     * The Optmobile
     */
    public String getOptmobile() {
        return Optmobile;
    }

    /**
     *
     * @param Optmobile
     * The Optmobile
     */
    public void setOptmobile(String Optmobile) {
        this.Optmobile = Optmobile;
    }

    /**
     *
     * @return
     * The Skypeid
     */
    public String getSkypeid() {
        return Skypeid;
    }

    /**
     *
     * @param Skypeid
     * The Skypeid
     */
    public void setSkypeid(String Skypeid) {
        this.Skypeid = Skypeid;
    }

}