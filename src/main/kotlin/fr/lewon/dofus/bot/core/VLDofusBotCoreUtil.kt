package fr.lewon.dofus.bot.core

import fr.lewon.dofus.bot.core.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.manager.VldbManager
import fr.lewon.dofus.bot.core.manager.d2o.D2OUtil
import fr.lewon.dofus.bot.core.manager.d2p.elem.D2PElementsAdapter
import fr.lewon.dofus.bot.core.manager.d2p.maps.D2PMapsAdapter
import fr.lewon.dofus.bot.core.manager.i18n.I18NUtil
import fr.lewon.dofus.bot.core.manager.world.WorldGraphUtil
import org.reflections.Reflections
import java.io.File

object VLDofusBotCoreUtil {

    fun initAll() {
        I18NUtil.init()
        initAllD2O()
        initAllD2P()
        initVldbManagers(VldbManager::class.java.packageName)
        initWorldGraph()
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
        D2PElementsAdapter.initStream("$mapsPath/elements.ele")
        File(mapsPath).listFiles()
            ?.filter { it.absolutePath.endsWith(".d2p") }
            ?.forEach { D2PMapsAdapter.initStream(it.absolutePath) }
            ?: error("Maps directory not found : $mapsPath}")
    }

    fun initVldbManagers(basePackageName: String) {
        Reflections(basePackageName)
            .getSubTypesOf(VldbManager::class.java)
            .mapNotNull { it.kotlin.objectInstance }
            .forEach { it.initManager() }
    }

    private fun initWorldGraph() {
        val worldGraphPath = "${VldbFilesUtil.getDofusDirectory()}/content/maps/worldgraph.bin"
        val worldGraphFile = File(worldGraphPath)
        if (!worldGraphFile.exists() || !worldGraphFile.isFile) {
            error("World graph file not found")
        }
        WorldGraphUtil.init(ByteArrayReader(worldGraphFile.readBytes()))
    }

}