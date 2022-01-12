package fr.lewon.dofus.bot.core.manager.d2o.managers

import fr.lewon.dofus.bot.core.manager.VldbManager
import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.charac.DofusCharacteristic

object CharacteristicManager : VldbManager {

    private lateinit var characteristicByKeyword: HashMap<String, DofusCharacteristic>
    private lateinit var characteristicById: HashMap<Double, DofusCharacteristic>

    override fun initManager() {
        characteristicByKeyword = HashMap()
        characteristicById = HashMap()
        D2OUtil.getObjects("Characteristics").map {
            val id = it["id"].toString().toDouble()
            val order = it["order"].toString().toInt()
            val keyWord = it["keyword"].toString()
            val categoryId = it["categoryId"].toString().toInt()
            DofusCharacteristic(id, order, categoryId, keyWord)
        }.forEach {
            characteristicById[it.id] = it
            characteristicByKeyword[it.keyWord] = it
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    fun getCharacteristic(keyWord: String): DofusCharacteristic? {
        return characteristicByKeyword[keyWord]
    }

    fun getCharacteristic(id: Double): DofusCharacteristic? {
        return characteristicById[id]
    }

}