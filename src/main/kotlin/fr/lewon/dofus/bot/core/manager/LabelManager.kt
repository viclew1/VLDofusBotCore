package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.manager.i18n.I18NUtil

object LabelManager : VldbManager {

    private lateinit var hintNameIdById: Map<Int, Int>
    private lateinit var subAreaNameIdById: Map<Int, Int>
    private lateinit var areaIdBySubAreaId: Map<Int, Int>
    private lateinit var areaNameIdById: Map<Int, Int>

    override fun initManager() {
        initPointOfInterest()
        initSubAreas()
        initAreas()
    }

    override fun getNeededManagers(): List<VldbManager> {
        return emptyList()
    }

    private fun initPointOfInterest() {
        val objects = D2OUtil.getObjects("PointOfInterest")
        hintNameIdById = objects.associate { buildIntIntEntry(it["id"], it["nameId"]) }
    }

    private fun initSubAreas() {
        val objects = D2OUtil.getObjects("SubAreas")
        subAreaNameIdById = objects.associate { buildIntIntEntry(it["id"], it["nameId"]) }
        areaIdBySubAreaId = objects.associate { buildIntIntEntry(it["id"], it["areaId"]) }
    }

    private fun initAreas() {
        val objects = D2OUtil.getObjects("Areas")
        areaNameIdById = objects.associate { buildIntIntEntry(it["id"], it["nameId"]) }
    }

    private fun buildIntIntEntry(key: Any?, value: Any?): Pair<Int, Int> {
        val keyInt = key?.toString()?.toIntOrNull() ?: error("Can build an entry for key : $key")
        val valueInt = value?.toString()?.toIntOrNull() ?: error("Can build an entry for key : $value")
        return Pair(keyInt, valueInt)
    }

    fun getHintLabel(hintLabelId: Int): String {
        val key = hintNameIdById[hintLabelId] ?: return "???"
        return I18NUtil.getLabel(key)
    }

    fun getSubAreaLabel(subAreaId: Int): String {
        val key = subAreaNameIdById[subAreaId] ?: error("No nameId found for subAreaId [$subAreaId]")
        return I18NUtil.getLabel(key)
    }

    fun getAreaLabel(subAreaId: Int): String {
        val areaId = areaIdBySubAreaId[subAreaId] ?: error("No areaId found for subAreaId [$subAreaId]")
        val key = areaNameIdById[areaId] ?: error("No nameId found for areaId [$areaId]")
        return I18NUtil.getLabel(key)
    }

}