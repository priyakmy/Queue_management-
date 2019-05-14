
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserPhotoModel {

    @SerializedName("coverPhoto")
    @Expose
    private String coverPhoto;
    @SerializedName("ipadPhoto")
    @Expose
    private String ipadPhoto;
    @SerializedName("mobilePhoto")
    @Expose
    private String mobilePhoto;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;

    /**
     * 
     * @return
     *     The coverPhoto
     */
    public String getCoverPhoto() {
        return coverPhoto;
    }

    /**
     * 
     * @param coverPhoto
     *     The coverPhoto
     */
    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    /**
     * 
     * @return
     *     The ipadPhoto
     */
    public String getIpadPhoto() {
        return ipadPhoto;
    }

    /**
     * 
     * @param ipadPhoto
     *     The ipadPhoto
     */
    public void setIpadPhoto(String ipadPhoto) {
        this.ipadPhoto = ipadPhoto;
    }

    /**
     * 
     * @return
     *     The mobilePhoto
     */
    public String getMobilePhoto() {
        return mobilePhoto;
    }

    /**
     * 
     * @param mobilePhoto
     *     The mobilePhoto
     */
    public void setMobilePhoto(String mobilePhoto) {
        this.mobilePhoto = mobilePhoto;
    }

    /**
     * 
     * @return
     *     The profilePhoto
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * 
     * @param profilePhoto
     *     The profilePhoto
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
