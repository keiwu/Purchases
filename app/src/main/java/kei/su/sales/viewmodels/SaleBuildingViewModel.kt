package kei.su.sales.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        }
    }

    suspend fun getSaleByManufacturer(manufacturer: String) : Double{
        var cost = buildingsRepository.getSaleByMan(manufacturer)
        return cost
    }

    suspend fun getSaleByCategory(cateId: String) : Double{
        var cost = buildingsRepository.getSaleByCategory(cateId)
        return cost
    }

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
