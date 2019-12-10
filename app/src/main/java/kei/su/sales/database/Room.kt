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

    @Query("select * from sales")
    fun getSales(): LiveData<List<DatabaseSale>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSale(vararg sales: DatabaseSale)

    @Query("select buildingId, sum(cost) as cost from sales group by buildingId")
    fun getCostByCountry(): LiveData<List<CountrySale>>

    @RawQuery(observedEntities = [DatabaseSale::class])
    fun getSaleByCategory(query: SupportSQLiteQuery):Double

    @Query("SELECT SUM(cost) as cost FROM sales WHERE manufacturer LIKE :manufact")
    fun getManufactuerSale(manufact : String): Double

    @Query("SELECT SUM(cost) as cost FROM sales WHERE item_category_id LIKE :catId")
    fun getCategorySale(catId : String): Double

    //method 1 for getting the country sale
    //    @Query("SELECT SUM(cost) as cost FROM sales WHERE buildingId in (SELECT buildingId FROM databasebuilding WHERE country LIKE :country)")
    //    fun getCountrySale(country : String): Double

    //method 2 for getting the country sale using inner join
    @Query("SELECT SUM(cost) as cost FROM sales INNER JOIN databasebuilding ON sales.buildingId = databasebuilding.buildingId WHERE country LIKE :country")
    fun getCountrySale(country : String): Double

    @Query("SELECT SUM(cost) as cost FROM sales INNER JOIN databasebuilding ON sales.buildingId = databasebuilding.buildingId WHERE state LIKE :state")
    fun getStateSale(state : String): Double

    @Query("SELECT count (*)  FROM sales INNER JOIN databasebuilding ON sales.buildingId = databasebuilding.buildingId WHERE item_id LIKE :itemId")
    fun getItemCount(itemId : String): Int

    @Query("SELECT SUM(cost) AS total, buildingName FROM sales INNER JOIN databasebuilding ON sales.buildingId = databasebuilding.buildingId GROUP BY sales.buildingId ORDER BY total DESC LIMIT 1")
    fun getBuildingWithMostSale(): List<BuildingSale>
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
