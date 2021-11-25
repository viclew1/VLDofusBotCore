package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil

object CharacteristicManager : VldbManager {

    private lateinit var characteristicIdByKeyword: Map<String, Int>

    override fun initManager() {
        val objects = D2OUtil.getObjects("Characteristics")
        characteristicIdByKeyword = objects.associate { buildStringIntEntry(it["keyword"], it["id"]) }
    }

    private fun buildStringIntEntry(key: Any?, value: Any?): Pair<String, Int> {
        val keyStr = key?.toString() ?: error("Can build an entry for key :  $key")
        val valueInt = value?.toString()?.toIntOrNull() ?: error("Can build an entry for key : $value")
        return Pair(keyStr, valueInt)
    }

    fun getCharacteristicId(keyWord: String): Int {
        return characteristicIdByKeyword[keyWord] ?: error("Characteristic [$keyWord] not found")
    }

}