package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.maps.DofusMap

object DofusMapManager : VldbManager {

    private lateinit var mapById: Map<Double, DofusMap>

    override fun initManager() {
        val objects = D2OUtil.getObjects("MapPositions")
        mapById = objects.associate {
            val id = getStringByKey(it, "id").toDouble()
            val posX = getStringByKey(it, "posX").toInt()
            val posY = getStringByKey(it, "posY").toInt()
            val subAreaId = getStringByKey(it, "subAreaId").toInt()
            val worldMap = getStringByKey(it, "worldMap").toInt()
            val isOutdoor = getStringByKey(it, "outdoor").toBoolean()
            val isTransition = getStringByKey(it, "isTransition").toBoolean()
            val hasPriorityOnWorldMap = getStringByKey(it, "hasPriorityOnWorldmap").toBoolean()
            id to DofusMap(subAreaId, worldMap, id, posX, posY, isOutdoor, isTransition, hasPriorityOnWorldMap)
        }
    }

    private fun getStringByKey(d2oObject: Map<String, Any>, key: String): String {
        val value = d2oObject[key]
        return value?.toString() ?: error("invalid map value for [$key] : $value")
    }

    fun getDofusMap(id: Double): DofusMap {
        return mapById[id] ?: error("No map found for id [$id]")
    }

    fun getDofusMaps(x: Int, y: Int): List<DofusMap> {
        return mapById.values.filter { it.posX == x && it.posY == y }
    }

}