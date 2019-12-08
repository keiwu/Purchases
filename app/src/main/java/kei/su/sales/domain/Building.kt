package kei.su.sales.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BuildingItem {

    @SerializedName("building_id")
    @Expose
    var buildingId: Int? = null
    @SerializedName("building_name")
    @Expose
    var buildingName: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null

}