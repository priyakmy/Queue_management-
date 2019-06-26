
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatdemographicsModel implements Serializable {

    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    @SerializedName("ContactId")
    @Expose
    private Integer contactId;
    @SerializedName("Dob")
    @Expose
    private String dob;
    @SerializedName("GenderId")
    @Expose
    private Integer genderId;
    @SerializedName("PatDemoid")
    @Expose
    private Integer patDemoid;
    @SerializedName("PatPic")
    @Expose
    private String patPic;
    @SerializedName("PatThumbPic")
    @Expose
    private String patThumbPic;
    @SerializedName("Patname")
    @Expose
    private String patname;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The addressId
     */

    public Integer getAddressId() {
        return addressId;
    }

    /**
     * 
     * @param addressId
     *     The AddressId
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     * 
     * @return
     *     The contactId
     */
    public Integer getContactId() {
        return contactId;
    }

    /**
     * 
     * @param contactId
     *     The ContactId
     */
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    /**
     * 
     * @return
     *     The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * 
     * @param dob
     *     The Dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * 
     * @return
     *     The genderId
     */
    public Integer getGenderId() {
        return genderId;
    }

    /**
     * 
     * @param genderId
     *     The GenderId
     */
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    /**
     * 
     * @return
     *     The patDemoid
     */
    public Integer getPatDemoid() {
        return patDemoid;
    }

    /**
     * 
     * @param patDemoid
     *     The PatDemoid
     */
    public void setPatDemoid(Integer patDemoid) {
        this.patDemoid = patDemoid;
    }

    /**
     * 
     * @return
     *     The patPic
     */
    public String getPatPic() {
        return patPic;
    }

    /**
     * 
     * @param patPic
     *     The PatPic
     */
    public void setPatPic(String patPic) {
        this.patPic = patPic;
    }

    /**
     * 
     * @return
     *     The patThumbPic
     */
    public String getPatThumbPic() {
        return patThumbPic;
    }

    /**
     * 
     * @param patThumbPic
     *     The PatThumbPic
     */
    public void setPatThumbPic(String patThumbPic) {
        this.patThumbPic = patThumbPic;
    }

    /**
     * 
     * @return
     *     The patname
     */
    public String getPatname() {
        return patname;
    }

    /**
     * 
     * @param patname
     *     The Patname
     */
    public void setPatname(String patname) {
        this.patname = patname;
    }

}