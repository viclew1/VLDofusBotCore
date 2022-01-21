package fr.lewon.dofus.bot.core.d2o.managers

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.maps.DofusMap

object WaypointsManager : VldbManager {

    lateinit var waypointsIds: List<Double>

    override fun initManager() {
        val objects = D2OUtil.getObjects("Waypoints")
        waypointsIds = objects.filter { it["activated"].toString().toBoolean() }
            .filter { !isSubAreaConquest(it["subAreaId"].toString().toDouble()) }
            .map { it["mapId"].toString().toDouble() }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    private fun isSubAreaConquest(subAreaId: Double): Boolean {
        return SubAreaManager.getSubArea(subAreaId).isConquestVillage
    }

    fun getAllZaapMaps(): List<DofusMap> {
        return waypointsIds.map { MapManager.getDofusMap(it) }
    }

}
