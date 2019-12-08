package kei.su.sales.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kei.su.sales.domain.Sale

@Entity(tableName = "sales")
data class DatabaseSale constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val buildingId: Int,
    val manufacturer: String,
    val item_id: Int,
    val item_category_id: Int,
    val cost: Double)

fun List<DatabaseSale>.asDomainModel(): List<Sale> {
    return map {
        Sale(
            id = it.id,
            buildingId = it.buildingId,
            manufacturer = it.manufacturer,
            item_id = it.item_id,
            item_category_id = it.item_category_id,
            cost = it.cost)
    }
}

