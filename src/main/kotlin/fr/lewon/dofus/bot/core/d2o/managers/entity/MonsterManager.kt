package fr.lewon.dofus.bot.core.d2o.managers.entity

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.i18n.I18NUtil
import fr.lewon.dofus.bot.core.model.entity.DofusMonster

object MonsterManager : VldbManager {

    private lateinit var monsterById: Map<Double, DofusMonster>

    override fun initManager() {
        monsterById = D2OUtil.getObjects("Monsters").associate {
            val id = it["id"].toString().toDouble()
            val nameId = it["nameId"].toString().toInt()
            val name = I18NUtil.getLabel(nameId) ?: "UNKNOWN_MONSTER_NAME"
            val isMiniBoss = it["isMiniBoss"].toString().toBoolean()
            id to DofusMonster(id, name, isMiniBoss)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    fun getMonster(monsterId: Double): DofusMonster {
        return monsterById[monsterId] ?: error("No monster for id : $monsterId")
    }

}