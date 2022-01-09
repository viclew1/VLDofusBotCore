package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.manager.i18n.I18NUtil
import fr.lewon.dofus.bot.core.model.hunt.DofusPointOfInterest

object PointOfInterestManager : VldbManager {

    private lateinit var poiById: Map<Double, DofusPointOfInterest>

    override fun initManager() {
        poiById = D2OUtil.getObjects("PointOfInterest").associate {
            val id = it["id"].toString().toDouble()
            val labelId = it["nameId"].toString().toInt()
            val label = I18NUtil.getLabel(labelId)
            id to DofusPointOfInterest(id, label)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    fun getPoi(poiId: Double): DofusPointOfInterest? {
        return poiById[poiId]
    }

}