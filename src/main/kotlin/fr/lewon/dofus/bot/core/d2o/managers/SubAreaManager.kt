package fr.lewon.dofus.bot.core.d2o.managers

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.i18n.I18NUtil
import fr.lewon.dofus.bot.core.model.maps.DofusSubArea

object SubAreaManager : VldbManager {

    private lateinit var subAreaById: Map<Double, DofusSubArea>

    override fun initManager() {
        subAreaById = D2OUtil.getObjects("SubAreas").associate {
            val id = it["id"].toString().toDouble()
            val worldMapId = it["worldmapId"].toString().toInt()
            val packId = it["packId"].toString().toInt()
            val isConquestVillage = it["isConquestVillage"].toString().toBoolean()
            val associatedZaapMapId = it["associatedZaapMapId"].toString().toDouble()
            val nameId = it["nameId"].toString().toInt()
            val name = I18NUtil.getLabel(nameId)
            val areaId = it["areaId"].toString().toDouble()
            val area = AreaManager.getArea(areaId)
            id to DofusSubArea(id, worldMapId, packId, isConquestVillage, associatedZaapMapId, name, area)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return listOf(AreaManager)
    }

    fun getAllSubAreas(): List<DofusSubArea> {
        return ArrayList(subAreaById.values)
    }

    fun getSubArea(subAreaId: Double): DofusSubArea {
        return subAreaById[subAreaId] ?: error("No sub area for id : $subAreaId")
    }

}