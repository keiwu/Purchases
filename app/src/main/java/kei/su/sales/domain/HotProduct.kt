package kei.su.sales.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class HotProduct{
	@SerializedName("product_category_id")
	@Expose
	val productCategoryId: Any? = null
	@SerializedName("hot_lists")
	@Expose
	private val hotLists: List<HotList>? = null
	@SerializedName("id")
	@Expose
	val id: String? = null
	@SerializedName("document_type")
	@Expose
	private val documentType: String? = null
	@SerializedName("_etag")
	@Expose
	private val etag: String? = null
	@SerializedName("_rid")
	@Expose
	private val rid: String? = null
	@SerializedName("_self")
	@Expose
	private val self: String? = null
	@SerializedName("_attachments")
	@Expose
	private val attachments: String? = null
	@SerializedName("_ts")
	@Expose
	private val ts: Int? = null
}
