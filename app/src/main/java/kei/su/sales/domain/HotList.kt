package kei.su.sales.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class HotList{
	@SerializedName("id")
	@Expose
	private val id: String? = null
	@SerializedName("name")
	@Expose
	private val name: String? = null
	@SerializedName("image_url")
	@Expose
	private val imageUrl: String? = null
	@SerializedName("product_ajins")
	@Expose
	private val productAjins: List<String>? = null

}