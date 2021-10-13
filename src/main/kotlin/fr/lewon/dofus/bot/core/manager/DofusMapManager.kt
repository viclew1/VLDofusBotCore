package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.maps.DofusMap

object DofusMapManager : VldbManager {

    private lateinit var mapById: Map<Double, DofusMap>

    override fun initManager() {
        val objects = D2OUtil.getObjects("MapPositions")
        mapById = objects.map {
            val id = getStringByKey(it, "id").toDouble()
            val posX = getStringByKey(it, "posX").toInt()
            val posY = getStringByKey(it, "posY").toInt()
            val subAreaId = getStringByKey(it, "subAreaId").toInt()
            val worldMap = getStringByKey(it, "worldMap").toInt()
            Pair(id, DofusMap(subAreaId, worldMap, id, posX, posY))
        }.toMap()
    }

    private fun getStringByKey(d2oObject: Map<String, Any>, key: String): String {
        val value = d2oObject[key]
        return value?.toString() ?: error("invalid map value for [$key] : $value")
    }

    fun getDofusMap(id: Double): DofusMap {
        return mapById[id] ?: error("No map found for id [$id]")
    }

}