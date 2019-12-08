package kei.su.sales.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SaleItem {

    @SerializedName("manufacturer")
    @Expose
    var manufacturer: String? = null
    @SerializedName("market_name")
    @Expose
    var marketName: String? = null
    @SerializedName("codename")
    @Expose
    var codename: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("usage_statistics")
    @Expose
    var usageStatistics: UsageStatistics? = null

}



class UsageStatistics {

    @SerializedName("session_infos")
    @Expose
    var sessionInfos: List<SessionInfo>? = null

}


class Purchase {

    @SerializedName("item_id")
    @Expose
    var itemId: Int? = null
    @SerializedName("item_category_id")
    @Expose
    var itemCategoryId: Int? = null
    @SerializedName("cost")
    @Expose
    var cost: Double? = null

}