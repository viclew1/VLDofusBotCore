package fr.lewon.dofus.bot.core

import fr.lewon.dofus.bot.core.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.core.manager.VldbManager
import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.manager.d2p.D2PUtil
import fr.lewon.dofus.bot.core.manager.i18n.I18NUtil
import org.reflections.Reflections
import java.io.File

object VLDofusBotCoreUtil {

    fun initAll() {
        I18NUtil.init()
        initAllD2O()
        initAllD2P()
        initVldbManagers()
    }

    private fun initAllD2O() {
        val d2oPath = "${VldbFilesUtil.getDofusDirectory()}/data/common"
        File(d2oPath).listFiles()
            ?.filter { it.absolutePath.endsWith(".d2o") }
            ?.forEach { D2OUtil.init(it.absolutePath) }
            ?: error("Maps directory not found : $d2oPath}")
    }

    private fun initAllD2P() {
        val mapsPath = "${VldbFilesUtil.getDofusDirectory()}/content/maps"
        File(mapsPath).listFiles()
            ?.filter { it.absolutePath.endsWith(".d2p") }
            ?.forEach { D2PUtil.initStream(it.absolutePath) ?: error("Failed to init stream") }
            ?: error("Maps directory not found : $mapsPath}")
    }

    private fun initVldbManagers() {
        Reflections("")
            .getSubTypesOf(VldbManager::class.java)
            .mapNotNull { it.kotlin.objectInstance }
            .forEach { it.initManager() }
    }

}