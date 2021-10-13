package fr.lewon.dofus.bot.core.manager

import fr.lewon.dofus.bot.core.manager.d2p.D2PUtil
import fr.lewon.dofus.bot.core.manager.d2p.maps.D2PMapsAdapter
import fr.lewon.dofus.bot.core.manager.d2p.maps.cell.CellData

object MapCellDataUtil {

    fun getCellDataList(mapId: Double, key: String): List<CellData> {
        val index = D2PUtil.indexes[mapId] ?: error("Missing map : $mapId")
        val fileStream = index.stream
        fileStream.setPosition(index.offset)
        val data = fileStream.readNBytes(index.length)
        return D2PMapsAdapter.loadFromData(data, key.toByteArray())
    }

}