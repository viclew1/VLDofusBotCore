package fr.lewon.dofus.bot.core.d2o.managers.map

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.maps.DofusMap

object WaypointManager : VldbManager {

    lateinit var waypoints: List<DofusMap>

    override fun initManager() {
        val objects = D2OUtil.getObjects("Waypoints")
        waypoints = objects.filter { it["activated"].toString().toBoolean() }
            .filter { !isSubAreaConquest(it["subAreaId"].toString().toDouble()) }
            .map { MapManager.getDofusMap(it["mapId"].toString().toDouble()) }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return listOf(MapManager)
    }

    private fun isSubAreaConquest(subAreaId: Double): Boolean {
        return SubAreaManager.getSubArea(subAreaId).isConquestVillage
    }

    fun getAllZaapMaps(): List<DofusMap> {
        return ArrayList(waypoints)
    }

}
