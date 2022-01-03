package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.model.interactive.DofusSkill

object SkillsManager : VldbManager {

    private lateinit var skillById: Map<Double, DofusSkill>

    override fun initManager() {
        skillById = D2OUtil.getObjects("Skills").associate {
            val id = it["id"].toString().toDouble()
            val elementActionId = it["elementActionId"].toString().toInt()
            id to DofusSkill(id, elementActionId)
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    fun getSkill(skillId: Double): DofusSkill? {
        return skillById[skillId]
    }

}