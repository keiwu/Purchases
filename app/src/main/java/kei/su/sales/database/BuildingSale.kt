package kei.su.sales.database

import androidx.room.ColumnInfo

data class BuildingSale(
    @ColumnInfo(name = "total") val total: Double?,
    @ColumnInfo(name = "buildingName") val buildingName: String?
)
