
package com.mcura.jaideep.queuemanagement.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderBoothDatum {

    @SerializedName("LabOrderdata")
    @Expose
    private List<LabOrderdatum> labOrderdata = null;
    @SerializedName("PharmacyOrderdata")
    @Expose
    private List<PharmacyOrderdatum> pharmacyOrderdata = null;

    public List<LabOrderdatum> getLabOrderdata() {
        return labOrderdata;
    }

    public void setLabOrderdata(List<LabOrderdatum> labOrderdata) {
        this.labOrderdata = labOrderdata;
    }

    public List<PharmacyOrderdatum> getPharmacyOrderdata() {
        return pharmacyOrderdata;
    }

    public void setPharmacyOrderdata(List<PharmacyOrderdatum> pharmacyOrderdata) {
        this.pharmacyOrderdata = pharmacyOrderdata;
    }

}
