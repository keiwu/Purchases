package kei.su.sales.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kei.su.sales.database.BuildingDatabase
import kei.su.sales.database.CountrySale
import kei.su.sales.database.asDomainModel
import kei.su.sales.domain.Building
import kei.su.sales.domain.BuildingItem
import kei.su.sales.domain.Sale
import kei.su.sales.domain.SaleItem
import kei.su.sales.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.sqlite.db.SimpleSQLiteQuery



class BuildingsRepository(private val database: BuildingDatabase) {

    /**
     * Buildings that can be shown on the screen.
     */
    val buildings: LiveData<List<Building>> =
        Transformations.map(database.saleBuildingDao.getBuildings()) {
            it.asDomainModel()
        }

    val sales: LiveData<List<Sale>> =
        Transformations.map(database.saleBuildingDao.getSales()) {
            it.asDomainModel()
        }

    val costs: LiveData<List<CountrySale>> =
        Transformations.map(database.saleBuildingDao.getCostByCountry()) {
            it.asDomainModel()
        }

    //lateinit var manufacturerSale: LiveData<Double>

    suspend fun getSaleByMan(manufacturer : String) : Double{
        var query = SimpleSQLiteQuery(
            "SELECT SUM(cost) as cost FROM sales WHERE manufacturer = ?",
            arrayOf(manufacturer)
        )
        var manufacturerSale = database.saleBuildingDao.getSaleByManufacturer(query)
        return manufacturerSale
    }

    suspend fun getSaleByCategory(catId : String) : Double{
        var query = SimpleSQLiteQuery(
            "SELECT SUM(cost) as cost FROM sales WHERE item_category_id = ?",
            arrayOf(catId)
        )
        var catSale = database.saleBuildingDao.getSaleByCategory(query)
        return catSale
    }

    suspend fun deleteABuilding(){
        database.saleBuildingDao.deleteBuilding()
    }

    /**
     * Refresh the buildings stored in the offline cache.
     */
    suspend fun refreshBuildings() {
        withContext(Dispatchers.IO) {
            val buildinglist = Network.repo.getBuildinglist().await()
            var networkBuildingContainer = updateNetworkBuildingContainer(buildinglist)
            database.saleBuildingDao.insertAll(*networkBuildingContainer.asDatabaseModel())
        }
    }

    suspend fun refreshSales() {
        withContext(Dispatchers.IO) {
            val salelist = Network.repo.getSalelist().await()
            var networkSaleContainer = updateNetworkSaleContainer(salelist)
            database.saleBuildingDao.insertAllSale(*networkSaleContainer.asDatabaseModel())
        }
    }

    private fun updateNetworkSaleContainer(salelist: List<SaleItem>): NetworkSaleContainer {
        var networkSalelist = arrayListOf<NetworkSale>()
        var id = 1

        for (s in salelist){
            val manufacturer = s.manufacturer
            val sessionInfos = s.usageStatistics?.sessionInfos
            if (sessionInfos!=null) {
                for (i in sessionInfos) {
                    val buildingId = i.buildingId
                    val purchases = i.purchases
                    if (purchases!=null){
                        for (k in purchases){
                            val itemId = k.itemId
                            val itemCatId = k.itemCategoryId
                            val cost = k.cost
                            val networkSale = NetworkSale(id, buildingId, manufacturer!!, itemId!!, itemCatId!!, cost!!)
                            networkSalelist.add(networkSale)
                            id++
                        }
                    }

                }
            }
        }

        var networkSaleContainer = NetworkSaleContainer(networkSalelist)


        return networkSaleContainer

    }


    private fun updateNetworkBuildingContainer(buildinglist: List<BuildingItem>) : NetworkBuildingContainer {
        var networkBuildinglist = arrayListOf<NetworkBuilding>()
        for (building in buildinglist){
            var netWorkBuilding = NetworkBuilding(building.buildingId!!,
                building.buildingName!!, building.city!!, building.state!!, building.country!!)
            networkBuildinglist.add(netWorkBuilding)
        }
        var networkBuildingContainer = NetworkBuildingContainer(networkBuildinglist)
        return networkBuildingContainer
    }


}
