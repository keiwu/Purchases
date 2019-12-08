package kei.su.sales.domain;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionInfo {

    @SerializedName("building_id")
    @Expose
    private Integer buildingId;
    @SerializedName("purchases")
    @Expose
    private List<Purchase> purchases = null;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

}