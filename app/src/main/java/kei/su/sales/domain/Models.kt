package kei.su.sales.domain

import android.provider.MediaStore.Video




/*
    Building for use locally for display UI
 */
data class Building(val buildingId: Int,
                 val buildingName: String,
                 val city: String,
                 val state: String,
                 val country: String)


data class Sale(val id: Int,
                val buildingId: Int,
                val manufacturer: String,
                val item_id: Int,
                val item_category_id: Int,
                val cost: Double)

