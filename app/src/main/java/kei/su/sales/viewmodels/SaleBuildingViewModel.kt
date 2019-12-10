package kei.su.sales.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kei.su.sales.database.getDatabase
import kei.su.sales.repository.BuildingsRepository
import kotlinx.coroutines.*

class SaleBuildingViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    val buildingsRepository = BuildingsRepository(database)

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        viewModelScope.launch {
            buildingsRepository.refreshBuildings()
            buildingsRepository.refreshSales()
            getBuildingWithMostSale()
        }
    }


    fun getSaleByManufacturer(manufacturer: String) {
       buildingsRepository.getSaleByMan(manufacturer)
    }

    fun getSaleByCategory(cateId: String){
        buildingsRepository.getSaleByCategory(cateId)
    }

    fun getSaleByCountry(country: String){
        buildingsRepository.getSaleByCountry(country)
    }

    fun getSaleByState(state: String){
        buildingsRepository.getSaleByState(state)
    }

    fun getItemCountById(itemId: String){
        buildingsRepository.getItemCount(itemId)
    }

    fun getBuildingWithMostSale(){
        viewModelScope.launch {
            buildingsRepository.getBuildingWithMostSale()
        }
    }

    var manufacturerSale = buildingsRepository.manufacturerSale
    var categorySale = buildingsRepository.categorySale
    var countrySale = buildingsRepository.countrySale
    var stateSale = buildingsRepository.stateSale
    var itemCount = buildingsRepository.itemCount
    var buildinngWithMostSale = buildingsRepository.buildingMostSale





    val buildinglist = buildingsRepository.buildings
    val salelist = buildingsRepository.sales
    val costs = buildingsRepository.costs

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SaleBuildingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SaleBuildingViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
