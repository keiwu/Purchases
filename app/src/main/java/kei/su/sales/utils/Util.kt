package kei.su.sales.utils

import kei.su.sales.domain.Building
import kei.su.sales.domain.Sale

class Util{
    companion object{
        fun createCountryList(buildings: List<Building>) : List<String> {
            var itemList = arrayListOf<String>()
            for (b in buildings){
                if (!itemList.contains(b.country))
                    itemList.add(b.country)
            }
            return itemList
        }

        fun createManufacturerList(sales: List<Sale>?): List<String> {
            var itemList = arrayListOf<String>()
            for (b in sales!!){
                if (!itemList.contains(b.manufacturer))
                    itemList.add(b.manufacturer)
            }
            return itemList
        }

        fun createCatList(sales: List<Sale>?): List<String> {
            var itemList = arrayListOf<String>()
            for (b in sales!!){
                if (!itemList.contains(b.item_category_id.toString()))
                    itemList.add(b.item_category_id.toString())
            }
            return itemList
        }
    }
}