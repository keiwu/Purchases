package kei.su.sales.network

import kei.su.sales.domain.BuildingItem
import kei.su.sales.domain.HotProduct
import kei.su.sales.domain.SaleItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by  on 4/13/2019.
 */
interface  NewsApiInterface{
//    @GET("v2/everything")
//    fun fetchLatestNewsAsync(@Query("q") query: String,
//                             @Query("sortBy") sortBy : String) : Deferred<Response<LatestNews>>


        @GET("GetBuildingData")
        fun getBuildinglist(): Deferred<Response<List<BuildingItem>>>

        @GET("GetAnalyticData")
        fun getSalelist(): Deferred<List<SaleItem>>



}