package fr.lewon.dofus.bot.core

import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.d2p.elem.D2PElementsAdapter
import fr.lewon.dofus.bot.core.d2p.maps.D2PMapsAdapter
import fr.lewon.dofus.bot.core.i18n.I18NUtil
import fr.lewon.dofus.bot.core.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.world.WorldGraphUtil
import org.reflections.Reflections
import java.io.File

object VldbCoreInitializer {

    fun initAll(mapsDecryptionKey: String, mapsDecryptionKeyCharset: String) {
        processInitialization({ I18NUtil.init() }, "Initializing I18N ... ")
        processInitialization({ initAllD2O() }, "Initializing D2O ... ")
        processInitialization({ initAllD2P(mapsDecryptionKey, mapsDecryptionKeyCharset) }, "Initializing D2P ... ")
        processInitialization({ initVldbManagers() }, "Initializing VLDB managers ... \n")
        processInitialization({ initWorldGraph() }, "Initializing world graph ... ")
    }

    private fun initAllD2O() {
        val d2oPath = "${VldbFilesUtil.getDofusDirectory()}/data/common"
        File(d2oPath).listFiles()
            ?.filter { it.absolutePath.endsWith(".d2o") }
            ?.forEach { D2OUtil.init(it.absolutePath) }
            ?: error("Maps directory not found : $d2oPath}")
    }

    private fun initAllD2P(mapsDecryptionKey: String, mapsDecryptionKeyCharset: String) {
        val mapsPath = "${VldbFilesUtil.getDofusDirectory()}/content/maps"
        D2PElementsAdapter.initStream("$mapsPath/elements.ele")
        File(mapsPath).listFiles()
            ?.filter { it.absolutePath.endsWith(".d2p") }
            ?.forEach { D2PMapsAdapter.initStream(it.absolutePath) }
            ?: error("Maps directory not found : $mapsPath}")
        D2PMapsAdapter.DECRYPTION_KEY = mapsDecryptionKey
        D2PMapsAdapter.DECRYPTION_KEY_CHARSET = mapsDecryptionKeyCharset
    }

    private fun initVldbManagers() {
        val basePackageName = VldbManager::class.java.packageName
        val managers = Reflections(basePackageName)
            .getSubTypesOf(VldbManager::class.java)
            .mapNotNull { it.kotlin.objectInstance }

        val initializedManagers = ArrayList<VldbManager>()
        managers.forEach { initManager(it, initializedManagers) }
    }

    private fun initManager(
        manager: VldbManager,
        initializedManagers: ArrayList<VldbManager>
    ) {
        manager.getNeededManagers().forEach {
            initManager(it, initializedManagers)
        }
        if (!initializedManagers.contains(manager)) {
            val startMessage = " - Initializing manager [${manager::class.java.simpleName}] ... "
            processInitialization({ manager.initManager() }, startMessage)
            initializedManagers.add(manager)
        }
    }

    private fun initWorldGraph() {
        val worldGraphPath = "${VldbFilesUtil.getDofusDirectory()}/content/maps/world-graph.binary"
        val worldGraphFile = File(worldGraphPath)
        if (!worldGraphFile.exists() || !worldGraphFile.isFile) {
            error("World graph file not found")
        }
        WorldGraphUtil.init(ByteArrayReader(worldGraphFile.readBytes()))
    }

    private fun processInitialization(initialization: () -> Unit, startMessage: String) {
        print(startMessage)
        val startTime = System.currentTimeMillis()
        initialization()
        val duration = System.currentTimeMillis() - startTime
        println("OK - $duration millis")
    }

}