package kei.su.sales.network

import com.squareup.moshi.JsonClass
import kei.su.sales.database.DatabaseBuilding
import kei.su.sales.database.DatabaseSale
import kei.su.sales.domain.Building
import kei.su.sales.domain.Sale


/**
 * BuildingHolder holds a list of Builldings.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "buildings": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkBuildingContainer(val buildings: List<NetworkBuilding>){
}

/**
 * Building that can be displayed.
 */
@JsonClass(generateAdapter = true)
data class NetworkBuilding(
    val building_id: Int,
    val building_name: String,
    val city: String,
    val state: String,
    val country: String)

/**
 * Convert Network results to database objects
 */
fun NetworkBuildingContainer.asDomainModel(): List<Building> {
    return buildings.map {
        Building(
            buildingId = it.building_id,
            buildingName = it.building_name,
            city = it.city,
            state = it.state,
            country = it.country)
    }
}

fun NetworkBuildingContainer.asDatabaseModel(): Array<DatabaseBuilding> {
    return buildings.map {
        DatabaseBuilding(
            buildingId = it.building_id,
            buildingName = it.building_name,
            city = it.city,
            state = it.state,
            country = it.country)
    }.toTypedArray()
}

@JsonClass(generateAdapter = true)
data class NetworkSaleContainer(val sales: List<NetworkSale>){
}

@JsonClass(generateAdapter = true)
data class NetworkSale(
    val id: Int,
    val building_id: Int,
    val manufacturer: String,
    val item_id: Int,
    val item_category_id: Int,
    val cost: Double
)

/**
 * Convert Network results to database objects
 */
fun NetworkSaleContainer.asDomainModel(): List<Sale> {
    return sales.map {
        Sale(
            id=it.id,
            buildingId = it.building_id,
            manufacturer = it.manufacturer,
            item_id = it.item_id,
            item_category_id = it.item_category_id,
            cost = it.cost)
    }
}

fun NetworkSaleContainer.asDatabaseModel(): Array<DatabaseSale> {
    return sales.map {
        DatabaseSale(
            id = it.id,
            buildingId = it.building_id,
            manufacturer = it.manufacturer,
            item_id = it.item_id,
            item_category_id = it.item_category_id,
            cost = it.cost)
    }.toTypedArray()
}
