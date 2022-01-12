package fr.lewon.dofus.bot.core.manager.d2o.managers

import fr.lewon.dofus.bot.core.manager.VldbManager
import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.manager.i18n.I18NUtil
import fr.lewon.dofus.bot.core.model.entity.DofusMonster

object MonstersManager : VldbManager {

    private lateinit var monsterById: HashMap<Double, DofusMonster>

    override fun initManager() {
        monsterById = HashMap()
        D2OUtil.getObjects("Monsters").forEach {
            val id = it["id"].toString().toDouble()
            val nameId = it["nameId"].toString().toInt()
            if (I18NUtil.hasLabel(nameId)) {
                val name = I18NUtil.getLabel(nameId)
                val isMiniBoss = it["isMiniBoss"].toString().toBoolean()
                monsterById[id] = DofusMonster(id, name, isMiniBoss)
            }
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    fun getMonster(monsterId: Double): DofusMonster {
        return monsterById[monsterId] ?: error("No monster for id : $monsterId")
    }

}