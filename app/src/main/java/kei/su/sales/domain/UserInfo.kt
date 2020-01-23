package kei.su.sales.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UserInfo{
    @SerializedName("business_account_list")
    @Expose
    private val businessAccountList: List<Any>? = null
    @SerializedName("merchant_list")
    @Expose
    private val merchantList: List<Any>? = null
    @SerializedName("gbm_flag")
    @Expose
    private val gbmFlag: Any? = null
    @SerializedName("business_account_flag")
    @Expose
    private val businessAccountFlag: Any? = null
    @SerializedName("merchant_flag")
    @Expose
    private val merchantFlag: Any? = null
    @SerializedName("address_book")
    @Expose
    private val addressBook: Any? = null
    @SerializedName("subjects")
    @Expose
    private val subjects: List<Subject>? = null
    @SerializedName("user_id")
    @Expose
    private val userId: String? = null
    @SerializedName("given_name")
    @Expose
    private val givenName: String? = null
    @SerializedName("family_name")
    @Expose
    private val familyName: String? = null
    @SerializedName("name")
    @Expose
    private val name: String? = null
    @SerializedName("email")
    @Expose
    private val email: String? = null
    @SerializedName("id")
    @Expose
    private val id: String? = null
    @SerializedName("document_type")
    @Expose
    private val documentType: String? = null
    @SerializedName("_etag")
    @Expose
    private val etag: String? = null
}

class Subject{
    @SerializedName("sub")
    @Expose
    private val sub: String? = null
    @SerializedName("id_provider")
    @Expose
    private val idProvider: Any? = null

}