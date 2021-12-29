package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.item.DofusItem
import fr.lewon.dofus.bot.core.model.item.DofusItemEffect

object ItemManager : VldbManager {

    private lateinit var itemById: Map<Double, DofusItem>

    override fun initManager() {
        val objects = D2OUtil.getObjects("Items")
        itemById = objects.associate { item ->
            val id = item["id"].toString().toDouble()
            val effects = (item["possibleEffects"] as List<Map<String, Any>>).mapNotNull { possibleEffect ->
                val effectId = possibleEffect["effectId"].toString().toDouble()
                val effect = D2OUtil.getObject("Effects", effectId) ?: error("Effect not found : $effectId")
                val characteristicId = effect["characteristic"].toString().toDouble()
                val min = possibleEffect["diceNum"].toString().toInt()
                val max = possibleEffect["diceSide"].toString().toInt()
                CharacteristicManager.getCharacteristic(characteristicId)?.let {
                    DofusItemEffect(min, max, it)
                }
            }
            id to DofusItem(id, effects)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return listOf(CharacteristicManager)
    }

    fun getItem(id: Double): DofusItem {
        return itemById[id] ?: error("No item found for id [$id]")
    }

}
