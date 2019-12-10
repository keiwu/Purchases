package kei.su.sales.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kei.su.sales.domain.Building

@Entity
data class DatabaseBuilding constructor(
    @PrimaryKey
    val buildingId: Int,
    val buildingName: String,
    val city: String,
    val state: String,
    val country: String)

fun List<DatabaseBuilding>.asDomainModel(): List<Building> {
    return map {
        Building(
            buildingId = it.buildingId,
            buildingName = it.buildingName,
            city = it.city,
            state = it.state,
            country = it.country)
    }
}


