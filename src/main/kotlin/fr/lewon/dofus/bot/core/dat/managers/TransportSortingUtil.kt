package fr.lewon.dofus.bot.core.dat.managers

import fr.lewon.dofus.bot.core.dat.DatUtil

object TransportSortingUtil {

    fun getZaapSortingMode(): SortingMode {
        val taxiData = getAccountTaxiData()
        val sortCriteria = taxiData["zaapSelection_sortCriteria"].toString()
        val descendingSort = taxiData["zaapSelection_descendingSort"].toString().toBoolean()
        return SortingMode(sortCriteria, descendingSort)
    }

    fun getZaapiSortingMode(): SortingMode {
        val taxiData = getAccountTaxiData()
        val sortCriteria = taxiData["zaapiSelection_sortCriteria"].toString()
        val descendingSort = taxiData["zaapiSelection_descendingSort"].toString().toBoolean()
        return SortingMode(sortCriteria, descendingSort)
    }

    private fun getAccountTaxiData(): HashMap<*, *> {
        return DatUtil.getDatFileContent("AccountModule_Ankama_Taxi.dat", HashMap::class.java)
            ?: HashMap<Any, Any>()
    }

    private fun getTaxiData(): HashMap<*, *> {
        return DatUtil.getDatFileContent("Module_Ankama_Taxi.dat", HashMap::class.java)
            ?: HashMap<Any, Any>()
    }

    fun getFavoriteZaapMapIds(): List<Double> {
        return getTaxiData()["favoriteZap"] as List<Double>? ?: emptyList()
    }

    data class SortingMode(val sortCriteria: String, val descendingSort: Boolean)

}