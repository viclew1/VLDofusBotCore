package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.maps.DofusSubArea

object SubAreaManager : VldbManager {

    private lateinit var subAreaById: Map<Double, DofusSubArea>

    override fun initManager() {
        subAreaById = D2OUtil.getObjects("SubAreas").associate {
            val id = it["id"].toString().toDouble()
            val isConquestVillage = it["isConquestVillage"].toString().toBoolean()
            id to DofusSubArea(id, isConquestVillage)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    fun getSubArea(subAreaId: Double): DofusSubArea? {
        return subAreaById[subAreaId]
    }

}