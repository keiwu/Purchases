package kei.su.sales.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.room.RawQuery



@Dao
interface SaleBuildingDao {
    @Query("select * from databasebuilding")
    fun getBuildings(): LiveData<List<DatabaseBuilding>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg buildings: DatabaseBuilding)

    @Query("delete from databasebuilding where buildingId == 1")
    fun deleteBuilding()

    @Query("select * from sales")
    fun getSales(): LiveData<List<DatabaseSale>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSale(vararg sales: DatabaseSale)

    @Query("select buildingId, sum(cost) as cost from sales group by buildingId")
    fun getCostByCountry(): LiveData<List<CountrySale>>

    @RawQuery(observedEntities = [DatabaseSale::class])
    fun getSaleByManufacturer(query: SupportSQLiteQuery):Double

    @RawQuery(observedEntities = [DatabaseSale::class])
    fun getSaleByCategory(query: SupportSQLiteQuery):Double
}

@Database(entities = [DatabaseBuilding::class, DatabaseSale::class], version = 1)
abstract class BuildingDatabase : RoomDatabase() {
    abstract val saleBuildingDao: SaleBuildingDao
}

private lateinit var INSTANCE: BuildingDatabase

fun getDatabase(context: Context): BuildingDatabase {
    synchronized(BuildingDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                BuildingDatabase::class.java,
                "buildings").build()
        }
    }
    return INSTANCE
}
