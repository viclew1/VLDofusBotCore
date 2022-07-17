package fr.lewon.dofus.bot.core.d2o.managers.map

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.i18n.I18NUtil
import fr.lewon.dofus.bot.core.model.maps.DofusHint
import fr.lewon.dofus.bot.core.model.maps.DofusMap

object HintManager : VldbManager {

    private const val ZAAP_GFX = 410

    private val hints = ArrayList<DofusHint>()

    override fun initManager() {
        hints.clear()
        for (hint in D2OUtil.getObjects("Hints")) {
            val id = hint["id"].toString().toInt()
            val outdoor = hint["outdoor"].toString().toBoolean()
            val worldMapId = hint["worldMapId"].toString().toInt()
            val worldMap = WorldMapManager.getWorldMap(worldMapId)
            val level = hint["level"].toString().toInt()
            val gfx = hint["gfx"].toString().toInt()
            val x = hint["x"].toString().toInt()
            val y = hint["y"].toString().toInt()
            val nameId = hint["nameId"].toString().toInt()
            val name = I18NUtil.getLabel(nameId) ?: "[INVALID_HINT_NAME]"
            val mapId = hint["mapId"].toString().toDouble()
            val map = MapManager.getDofusMap(mapId)
            val categoryId = hint["categoryId"].toString().toInt()
            hints.add(DofusHint(id, outdoor, worldMap, level, gfx, x, y, name, map, categoryId))
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return listOf(MapManager)
    }

    fun getAllZaapMaps(): List<DofusMap> {
        return hints.filter { it.gfx == ZAAP_GFX }.map { it.map }
    }
}