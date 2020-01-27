package kei.su.sales.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.http.GET
import kei.su.sales.domain.BuildingItem
import kei.su.sales.domain.HotProduct
import kei.su.sales.domain.SaleItem
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface RepositoryService {
//    @GET("GetBuildingData")
//    fun getBuildinglist(): Deferred<List<BuildingItem>>

    @GET("GetBuildingData")
    fun getBuildinglist(): Call<List<BuildingItem>>

    @GET("GetAnalyticData")
    fun getSalelist(): Deferred<List<SaleItem>>

    @GET("setting/hotlist")
    fun getHotList(): Deferred<HotProduct>


}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Main entry point for network access. Call like `Network.devbytes.getPlaylist()`
 */
object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://positioning-test.mapsted.com/api/Values/")
        //.baseUrl("https://az01d-coreapi-apim.azure-api.net/settings/v1/")
        //.addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val repo = retrofit.create(RepositoryService::class.java)
}