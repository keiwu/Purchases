package kei.su.sales.database

fun List<ManufactuerSale>.asDomainModel(): List<ManufactuerSale> {
    return map {
        ManufactuerSale(
            cost = it.cost)
    }
}

data class ManufactuerSale(
    val cost: Double
)