package fr.lewon.dofus.bot.core.d2p.gfx

import fr.lewon.dofus.bot.core.d2p.AbstractLinkedD2PUrlLoaderAdapter

object D2PGfxAdapter : AbstractLinkedD2PUrlLoaderAdapter(-1) {

    override fun getId(filePath: String): Double {
        return Regex(".*?/([0-9]+)\\..*").find(filePath)?.destructured?.component1()?.toDouble()
            ?: error("Invalid key")
    }

    fun getGfxImageDataById(gfxId: Double): ByteArray {
        val index = indexes[gfxId] ?: error("Missing gfx : $gfxId")
        val fileStream = index.stream
        fileStream.setPosition(index.offset)
        return fileStream.readNBytes(index.length)
    }

}