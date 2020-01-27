package khalid.com.newssearcherv4.repositories

import kei.su.sales.domain.BuildingItem
import kei.su.sales.network.NewsApiInterface


/**
 * Created by  on 4/13/2019.
 */
class NewsRepo(private val apiInterface: NewsApiInterface) : BaseRepository() {
    suspend fun getBuildingList() :  List<BuildingItem>?{

        return safeApiCall(
                call = {apiInterface.getBuildinglist().await()},
                error = "Error fetching news"
        )
    }
}