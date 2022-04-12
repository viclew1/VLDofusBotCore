package fr.lewon.dofus.bot.core.dat.managers

import fr.lewon.dofus.bot.core.dat.DatUtil
import fr.lewon.dofus.bot.core.ui.UIPoint

object DofusUIPositionsManager {

    const val CONTEXT_DEFAULT = "default"
    const val CONTEXT_FIGHT = "fight"

    fun getZaapSelectionUiPosition(): UIPoint? {
        return getUIPoint("zaapSelection", "pos", "window316", CONTEXT_DEFAULT)
    }

    fun getBannerUiPosition(context: String = CONTEXT_DEFAULT): UIPoint? {
        return getUIPoint("banner", "pos", "mainCtr", context)
    }

    fun getTreasureHuntUiPosition(): UIPoint? {
        return getUIPoint("treasureHunt", "pos", "ctr_hunt", CONTEXT_DEFAULT)
    }

    fun getArenaUiPosition(): UIPoint? {
        return getUIPoint("pvpArena", "pos", "window827", CONTEXT_DEFAULT)
    }

    private fun getUIPoint(uiItemKey: String, infoType: String, ctr: String, context: String): UIPoint? {
        val key = buildKey(uiItemKey, infoType, ctr, context)
        return DatUtil.getDatFileContent("Berilia_ui_positions", DofusUIPointByKey::class.java)?.get(key)
    }

    private fun buildKey(uiItemKey: String, infoType: String, ctr: String, context: String): String {
        return "$uiItemKey##$infoType##$ctr##$context"
    }

    private class DofusUIPointByKey : HashMap<String, UIPoint?>()
}