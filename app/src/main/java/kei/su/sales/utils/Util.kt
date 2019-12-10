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

        fun createCatList(sales: List<Sale>?): List<Int> {
            var itemList = arrayListOf<Int>()
            for (b in sales!!){
                if (!itemList.contains(b.item_category_id))
                    itemList.add(b.item_category_id)
            }

            itemList.sort()

            return itemList
        }

        fun createStateList(buildings: List<Building>): List<String> {
            var itemList = arrayListOf<String>()
            for (b in buildings){
                if (!itemList.contains(b.state))
                    itemList.add(b.state)
            }
            return itemList

        }

        fun createItemIdList(sales: List<Sale>?): List<Int> {
            var itemList = arrayListOf<Int>()
            for (b in sales!!){
                if (!itemList.contains(b.item_id))
                    itemList.add(b.item_id)
            }

            itemList.sort()

            return itemList

        }
    }
}