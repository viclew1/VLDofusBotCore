package fr.lewon.dofus.bot.core.ui.managers

import fr.lewon.dofus.bot.core.ui.UIPoint
import fr.lewon.dofus.bot.core.ui.dat.DatUtil
import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Container

enum class DofusUIElement(private val key: String, private val ctr: String = "mainCtr") {

    INVENTORY("storage", "equipmentUi"),
    ZAAP_SELECTION("zaapSelection", "window316"),
    BANNER("banner", "mainCtr"),
    TREASURE_HUNT("treasureHunt", "ctr_hunt"),
    ARENA("pvpArena", "window824");

    companion object {
        private const val CONTEXT_DEFAULT = "default"
        private const val CONTEXT_FIGHT = "fight"

        private fun getUIPoints(): DofusUIPointByKey {
            return DatUtil.getDatFileContent("Berilia_ui_positions", DofusUIPointByKey::class.java)
                ?: error("Couldn't get UI positions")
        }

        private class DofusUIPointByKey : HashMap<String, UIPoint?>()
    }

    fun getPosition(fightContext: Boolean = false): UIPoint {
        return getUIPoints()[buildPosKey(fightContext)] ?: getDefaultContainer().position
    }

    fun getSize(fightContext: Boolean = false): UIPoint {
        val overriddenPosition = getUIPoints()[buildSizeKey(fightContext)] ?: UIPoint()
        return overriddenPosition.transpose(UiDataManager.getContainer(key, ctr).size)
    }

    fun getDefaultContainer(): Container {
        return UiDataManager.getContainer(key, ctr)
    }

    private fun buildPosKey(fightContext: Boolean): String {
        return buildKey(fightContext, "pos")
    }

    private fun buildSizeKey(fightContext: Boolean): String {
        return buildKey(fightContext, "size")
    }

    private fun buildKey(fightContext: Boolean, infoType: String): String {
        val context = if (fightContext) CONTEXT_FIGHT else CONTEXT_DEFAULT
        return "$key##$infoType##$ctr##$context"
    }

}