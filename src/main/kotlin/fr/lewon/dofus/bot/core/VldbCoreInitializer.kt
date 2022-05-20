package fr.lewon.dofus.bot.core

import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.d2p.elem.D2PElementsAdapter
import fr.lewon.dofus.bot.core.d2p.maps.D2PMapsAdapter
import fr.lewon.dofus.bot.core.i18n.I18NUtil
import fr.lewon.dofus.bot.core.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.ui.managers.XmlUiUtil
import fr.lewon.dofus.bot.core.ui.xml.modele.ui.UiModule
import fr.lewon.dofus.bot.core.world.WorldGraphUtil
import org.reflections.Reflections
import java.io.File

object VldbCoreInitializer {

    var DEBUG = false

    fun initAll(mapsDecryptionKey: String, mapsDecryptionKeyCharset: String) {
        processInitialization({ I18NUtil.init() }, "Initializing I18N ... ")
        processInitialization({ initAllD2O() }, "Initializing D2O ... ")
        processInitialization({ initAllD2P(mapsDecryptionKey, mapsDecryptionKeyCharset) }, "Initializing D2P ... ")
        processInitialization({ initVldbManagers() }, "Initializing VLDB managers ... \n")
        processInitialization({ initWorldGraph() }, "Initializing world graph ... ")
        processInitialization({ initUIGroups() }, "Initializing UI Groups ... ")
        processInitialization({ initUIXml() }, "Initializing UI XML ... ")
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

    private fun initUIGroups() {
        val xmlUiPath = "${VldbFilesUtil.getDofusDirectory()}/ui"
        val subDirs = File(xmlUiPath).listFiles() ?: error("XML directory not found : $xmlUiPath}")
        for (subDir in subDirs) {
            val d2uiFile = subDir.listFiles()?.firstOrNull { it.absolutePath.endsWith(".d2ui") }
                ?: continue
            UiModule.init(ByteArrayReader(d2uiFile.readBytes()))
        }
    }

    private fun initUIXml() {
        val xmlUiPath = "${VldbFilesUtil.getDofusDirectory()}/ui"
        val subDirs = File(xmlUiPath).listFiles() ?: error("XML directory not found : $xmlUiPath}")
        for (subDir in subDirs) {
            val uiDir = subDir.listFiles()?.firstOrNull { it.isDirectory && it.name.equals("ui") }
                ?: continue
            val xmlFiles = uiDir.listFiles()?.filter { it.absolutePath.endsWith(".xml") }
                ?: error("XML files not found : ${uiDir.absolutePath}")
            xmlFiles.forEach { XmlUiUtil.init(it) }
        }
    }

    private fun processInitialization(initialization: () -> Unit, startMessage: String) {
        print(startMessage)
        val startTime = System.currentTimeMillis()
        initialization()
        val duration = System.currentTimeMillis() - startTime
        println("OK - $duration millis")
    }

}