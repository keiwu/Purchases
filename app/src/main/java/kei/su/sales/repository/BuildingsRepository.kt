package kei.su.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kei.su.sales.domain.Building
import kei.su.sales.domain.BuildingItem
import kei.su.sales.domain.Sale
import kei.su.sales.domain.SaleItem
import kei.su.sales.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kei.su.sales.database.*
import retrofit2.Callback

import retrofit2.Call
import retrofit2.Response


class BuildingsRepository(private val database: BuildingDatabase) {

    /**
     * Buildings that can be shown on the screen.
     */
    val buildings: LiveData<List<Building>> =
        Transformations.map(database.saleBuildingDao.getBuildings()) {
            it.asDomainModel()
        }

    //get all the rows from sales table
    val sales: LiveData<List<Sale>> =
        Transformations.map(database.saleBuildingDao.getSales()) {
            it.asDomainModel()
        }

    val costs: LiveData<List<CountrySale>> =
        Transformations.map(database.saleBuildingDao.getCostByCountry()) {
            it.asDomainModel()
        }

    var manufacturerSale = MutableLiveData<Double> ()
    var categorySale = MutableLiveData<Double> ()
    var countrySale = MutableLiveData<Double> ()
    var stateSale = MutableLiveData<Double> ()
    var itemCount = MutableLiveData<Int> ()
    var buildingMostSale = MutableLiveData<List<BuildingSale>> ()

    fun getSaleByMan(manufacturer : String) {
        manufacturerSale.postValue(database.saleBuildingDao.getManufactuerSale(manufacturer))
    }

    fun getSaleByCategory(catId : String){
        categorySale.postValue(database.saleBuildingDao.getCategorySale(catId))
    }

    fun getSaleByCountry(country : String){
        countrySale.postValue(database.saleBuildingDao.getCountrySale(country))
    }

    fun getSaleByState(state : String){
        stateSale.postValue(database.saleBuildingDao.getStateSale(state))
    }

    fun getItemCount(itemId : String){
        itemCount.postValue(database.saleBuildingDao.getItemCount(itemId))
    }

    suspend fun getBuildingWithMostSale(){


        withContext(Dispatchers.IO) {
            buildingMostSale.postValue(database.saleBuildingDao.getBuildingWithMostSale())
            var temp = database.saleBuildingDao.getBuildingWithMostSale()
            println(temp.size)
        }


    }

    /**
     * Refresh the buildings stored in the offline cache.
     */
    suspend fun refreshBuildings() {
        withContext(Dispatchers.IO) {
//            val hotlist = Network.repo.getHotList().await()
//            Log.d("hotlist", hotlist.id.toString())
            val repoApi = Network.repo
            val call = repoApi.getBuildinglist()

            //call.enqueue(Callback<List<BuildingItem>>)

            call.enqueue(object : Callback<List<BuildingItem>> {
                override fun onResponse(call: Call<List<BuildingItem>>, response: Response<List<BuildingItem>>) {

                    if (!response.isSuccessful()) {
                        return
                    }

                    val buildings = response.body()
                    Log.d("buildingsResult", "buildings size " + buildings!!.size)


                }

                override fun onFailure(call: Call<List<BuildingItem>>, t: Throwable) {
                    println("fail to get result")
                }
            })


//            val buildinglist = repo.getBuildinglist().await()
//            var networkBuildingContainer = updateNetworkBuildingContainer(buildinglist)
//            database.saleBuildingDao.insertAll(*networkBuildingContainer.asDatabaseModel())
        }
    }

    suspend fun refreshSales() {
        withContext(Dispatchers.IO) {
            val repo = Network.repo
            try {
                val salelist = repo.getSalelist().await()
                var networkSaleContainer = updateNetworkSaleContainer(salelist)
                database.saleBuildingDao.insertAllSale(*networkSaleContainer.asDatabaseModel())
            }catch (e:Exception){
                println("caught an exception $e")
            }

        }
    }


    /*
        Build the network sale container from retrieved data
     */
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
