package kei.su.sales.database

import kei.su.sales.domain.Sale

fun List<CountrySale>.asDomainModel(): List<CountrySale> {
    return map {
        CountrySale(
            buildingId = it.buildingId,
            cost = it.cost)
    }
}

data class CountrySale constructor(
    val buildingId: Int,
    val cost: Double
)