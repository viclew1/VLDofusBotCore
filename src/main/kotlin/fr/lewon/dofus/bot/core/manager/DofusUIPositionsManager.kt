package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.dat.DatUtil

object DofusUIPositionsManager : VldbManager {

    private const val INFO_TYPE_POSITION = "pos"
    private const val INFO_TYPE_SIZE = "size"

    const val CONTEXT_DEFAULT = "default"
    const val CONTEXT_FIGHT = "fight"

    private const val CTR_HUNT = "ctr_hunt"
    private const val CTR_MAIN = "ctr_main"
    private const val MAIN_CTR = "mainCtr"

    override fun initManager() {
        // Nothing
    }

    fun getBannerUiPosition(context: String = CONTEXT_DEFAULT): UIPoint {
        return getUIPoint("banner", INFO_TYPE_POSITION, MAIN_CTR, context)
    }

    fun getBannerMapUiPosition(): UIPoint {
        return getUIPoint("bannerMap", INFO_TYPE_POSITION, MAIN_CTR, CONTEXT_DEFAULT)
    }

    fun getBannerMapUiSize(): UIPoint {
        return getUIPoint("bannerMap", INFO_TYPE_SIZE, MAIN_CTR, CONTEXT_DEFAULT)
    }

    fun getBannerMenuUiPosition(context: String = CONTEXT_DEFAULT): UIPoint {
        return getUIPoint("bannerMenu", INFO_TYPE_POSITION, MAIN_CTR, context)
    }

    fun getBannerMenuUiSize(): UIPoint {
        return getUIPoint("bannerMenu", INFO_TYPE_SIZE, MAIN_CTR, CONTEXT_DEFAULT)
    }

    fun getBuffUiPosition(): UIPoint {
        return getUIPoint("buffUi", INFO_TYPE_POSITION, MAIN_CTR, CONTEXT_DEFAULT)
    }

    fun getChatUiPosition(context: String = CONTEXT_DEFAULT): UIPoint {
        return getUIPoint("chat", INFO_TYPE_POSITION, MAIN_CTR, context)
    }

    fun getChatUiSize(): UIPoint {
        return getUIPoint("chat", INFO_TYPE_SIZE, MAIN_CTR, CONTEXT_DEFAULT)
    }

    fun getExternalActionBarUiPosition(actionBarId: Int): UIPoint {
        return getUIPoint("externalActionBar_$actionBarId", INFO_TYPE_POSITION, MAIN_CTR, CONTEXT_DEFAULT)
    }

    fun getQuestListUiPosition(): UIPoint {
        return getUIPoint("questList", INFO_TYPE_POSITION, CTR_MAIN, CONTEXT_DEFAULT)
    }

    fun getTreasureHuntUiPosition(): UIPoint {
        return getUIPoint("treasureHunt", INFO_TYPE_POSITION, CTR_HUNT, CONTEXT_DEFAULT)
    }

    private fun getUIPoint(uiItemKey: String, infoType: String, ctr: String, context: String): UIPoint {
        val key = buildKey(uiItemKey, infoType, ctr, context)
        val uiPointByKey = DatUtil.getDatFileContent("Berilia_ui_positions", DofusUIPointByKey::class.java)
        return uiPointByKey[key] ?: error("No UI position for key : $key")
    }

    private fun buildKey(uiItemKey: String, infoType: String, ctr: String, context: String): String {
        return "$uiItemKey##$infoType##$ctr##$context"
    }

    class UIPoint(var x: Float = 0f, var y: Float = 0f)

    private class DofusUIPointByKey : HashMap<String, UIPoint>()
}