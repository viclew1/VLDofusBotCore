package fr.lewon.dofus.bot.core.d2o.managers

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.item.DofusItem
import fr.lewon.dofus.bot.core.model.item.DofusItemEffect

object ItemManager : VldbManager {

    private lateinit var itemById: Map<Double, DofusItem>

    override fun initManager() {
        val objects = D2OUtil.getObjects("Items")
        itemById = objects.associate { item ->
            val id = item["id"].toString().toDouble()
            val possibleEffects = (item["possibleEffects"] as List<Map<String, Any>>).mapNotNull { possibleEffect ->
                val effectId = possibleEffect["effectId"].toString().toDouble()
                val characteristic = EffectManager.getCharacteristicByEffectId(effectId)
                val min = possibleEffect["diceNum"].toString().toInt()
                val max = possibleEffect["diceSide"].toString().toInt()
                characteristic?.let { DofusItemEffect(min, max, it) }
            }
            id to DofusItem(id, possibleEffects)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return listOf(EffectManager)
    }

    fun getItem(id: Double): DofusItem {
        return itemById[id] ?: error("No item found for id [$id]")
    }

}
