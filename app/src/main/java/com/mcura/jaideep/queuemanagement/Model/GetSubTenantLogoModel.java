
package com.mcura.jaideep.queuemanagement.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSubTenantLogoModel {

    @SerializedName("mobileLogo")
    @Expose
    private String mobileLogo;
    @SerializedName("subTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("tabLogo")
    @Expose
    private String tabLogo;
    @SerializedName("webLogo")
    @Expose
    private String webLogo;

    /**
     * 
     * @return
     *     The mobileLogo
     */
    public String getMobileLogo() {
        return mobileLogo;
    }

    /**
     * 
     * @param mobileLogo
     *     The mobileLogo
     */
    public void setMobileLogo(String mobileLogo) {
        this.mobileLogo = mobileLogo;
    }

    /**
     * 
     * @return
     *     The subTenantId
     */
    public Integer getSubTenantId() {
        return subTenantId;
    }

    /**
     * 
     * @param subTenantId
     *     The subTenantId
     */
    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    /**
     * 
     * @return
     *     The tabLogo
     */
    public String getTabLogo() {
        return tabLogo;
    }

    /**
     * 
     * @param tabLogo
     *     The tabLogo
     */
    public void setTabLogo(String tabLogo) {
        this.tabLogo = tabLogo;
    }

    /**
     * 
     * @return
     *     The webLogo
     */
    public String getWebLogo() {
        return webLogo;
    }

    /**
     * 
     * @param webLogo
     *     The webLogo
     */
    public void setWebLogo(String webLogo) {
        this.webLogo = webLogo;
    }

}
