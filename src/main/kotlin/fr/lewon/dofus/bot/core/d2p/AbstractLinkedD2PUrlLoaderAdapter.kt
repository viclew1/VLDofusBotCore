package fr.lewon.dofus.bot.core.d2p

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import java.io.File

abstract class AbstractLinkedD2PUrlLoaderAdapter(loaderHeader: Int) : AbstractD2PUrlLoaderAdapter(loaderHeader) {

    protected val indexes = HashMap<Double, D2PIndex>()
    protected val properties = HashMap<String, String>()

    override fun initStream(path: String) {
        var filePath = path
        var file: File? = File(filePath)
        var stream: ByteArrayReader
        while (file != null && file.exists()) {
            stream = ByteArrayReader(file.readBytes())
            val vMax = stream.readByte().toInt()
            val vMin = stream.readByte().toInt()
            if (vMax != 2 || vMin != 1) {
                error("Invalid linked D2P file : $path")
            }
            stream.setPosition(file.length().toInt() - 24)
            val dataOffset = stream.readInt()
            val dataCount = stream.readInt()
            val indexOffset = stream.readInt()
            val indexCount = stream.readInt()
            val propertiesOffset = stream.readInt()
            val propertiesCount = stream.readInt()
            stream.setPosition(propertiesOffset)
            file = null
            for (i in 0 until propertiesCount) {
                val propertyName = stream.readUTF()
                val propertyValue = stream.readUTF()
                properties[propertyName] = propertyValue
                if (propertyName == "link") {
                    val idx = filePath.lastIndexOf("/")
                    filePath = if (idx != -1) {
                        filePath.substring(0, idx) + "/" + propertyValue
                    } else {
                        propertyValue
                    }
                    file = File(filePath)
                }
            }
            stream.setPosition(indexOffset)
            for (i in 0 until indexCount) {
                filePath = stream.readUTF()
                val fileOffset = stream.readInt()
                val fileLength = stream.readInt()
                indexes[getId(filePath)] = D2PIndex(fileOffset + dataOffset, fileLength, path)
            }
        }
    }

    protected abstract fun getId(filePath: String): Double

}