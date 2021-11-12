package fr.lewon.dofus.bot.core.manager.d2p.elem

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.manager.d2p.AbstractD2PUrlLoaderAdapter
import fr.lewon.dofus.bot.core.manager.d2p.elem.graphical.GraphicalElementData
import fr.lewon.dofus.bot.core.manager.d2p.elem.graphical.GraphicalElementFactory
import java.io.File

object D2PElementsAdapter : AbstractD2PUrlLoaderAdapter(69) {

    private lateinit var stream: ByteArrayReader
    private var fileVersion = -1
    private val elementsMap = HashMap<Int, GraphicalElementData>()
    private val elementsIndex = HashMap<Int, Int>()

    fun getElement(elementId: Int): GraphicalElementData {
        return elementsMap[elementId] ?: readElement(elementId)
    }

    override fun initStream(path: String) {
        val file = File(path)
        if (!file.exists()) {
            error("Elements file not found : $path")
        }
        stream = loadFromData(file.readBytes())
        val header = stream.readByte().toInt()
        if (header != loaderHeader) {
            error("Unknown file format for elements : $header")
        }
        fileVersion = stream.readByte().toInt()
        val elementsCount = stream.readInt()
        var skypLen = 0
        for (i in 0 until elementsCount) {
            if (fileVersion >= 9) {
                skypLen = stream.readUnsignedShort()
            }
            val elementId = stream.readInt()
            elementsIndex[elementId] = stream.getPosition()
            if (fileVersion <= 8) {
                elementsMap[elementId] = readElement(elementId)
            } else {
                stream.skip(skypLen - 4)
            }
        }
        if (fileVersion >= 8) {
            val gfxCount = stream.readInt()
            val jpgMap = HashMap<Int, Boolean>()
            for (i in 0 until gfxCount) {
                val gfxId = stream.readInt()
                jpgMap[gfxId] = true
            }
        }
    }

    private fun readElement(edId: Int): GraphicalElementData {
        val index = elementsIndex[edId] ?: error("Element not found : $edId")
        stream.setPosition(index)
        return GraphicalElementFactory.getGraphicalElementData(edId, stream.readUnsignedByte())
            .also { it.deserialize(stream, fileVersion) }
    }

}