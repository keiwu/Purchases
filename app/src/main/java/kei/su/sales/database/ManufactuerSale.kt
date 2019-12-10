package kei.su.sales.database

import kei.su.sales.domain.Sale

fun List<ManufactuerSale>.asDomainModel(): List<ManufactuerSale> {
    return map {
        ManufactuerSale(
            cost = it.cost)
    }
}

data class ManufactuerSale(
    val cost: Double
)