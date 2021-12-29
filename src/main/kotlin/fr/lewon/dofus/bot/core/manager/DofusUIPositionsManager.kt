package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.dat.DatUtil
import fr.lewon.dofus.bot.core.manager.ui.UIPoint

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

    private fun getUIPoint(uiItemKey: String, infoType: String, ctr: String, context: String): UIPoint? {
        val key = buildKey(uiItemKey, infoType, ctr, context)
        val uiPointByKey = DatUtil.getDatFileContent("Berilia_ui_positions", DofusUIPointByKey::class.java)
        return uiPointByKey[key]
    }

    private fun buildKey(uiItemKey: String, infoType: String, ctr: String, context: String): String {
        return "$uiItemKey##$infoType##$ctr##$context"
    }

    private class DofusUIPointByKey : HashMap<String, UIPoint?>()
}