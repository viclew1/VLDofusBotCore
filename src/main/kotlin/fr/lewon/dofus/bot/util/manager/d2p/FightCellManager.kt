package fr.lewon.dofus.bot.util.manager.d2p

import fr.lewon.dofus.bot.util.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.util.manager.VldbManager
import fr.lewon.dofus.bot.util.manager.d2p.maps.D2PMapsAdapter
import fr.lewon.dofus.bot.util.manager.d2p.maps.cell.CellData
import java.io.File

object FightCellManager : VldbManager {

    const val MAP_CELLS_COUNT = 560

    override fun initManager() {
        val mapsPath = "${VldbFilesUtil.getDofusDirectory()}/content/maps"
        File(mapsPath).listFiles()
            ?.filter { it.absolutePath.endsWith(".d2p") }
            ?.forEach { D2PUtil.initStream(it.absolutePath) ?: error("Failed to init stream") }
            ?: error("Maps directory not found : $mapsPath}")
    }

    fun getCellDataList(mapId: Double, key: String): List<CellData> {
        val index = D2PUtil.indexes[mapId] ?: error("Missing map : $mapId")
        val fileStream = index.stream
        fileStream.setPosition(index.offset)
        val data = fileStream.readNBytes(index.length)
        return D2PMapsAdapter.loadFromData(data, key.toByteArray())
    }

}