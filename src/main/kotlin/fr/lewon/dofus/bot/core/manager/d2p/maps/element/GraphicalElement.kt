package fr.lewon.dofus.bot.core.manager.d2p.maps.element

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.manager.d2p.maps.cell.Cell


class GraphicalElement(cell: Cell) : BasicElement(cell) {

    override fun read(mapVersion: Int, stream: ByteArrayReader) {
        stream.readInt()
        stream.readByte()
        stream.readByte()
        stream.readByte()
        stream.readByte()
        stream.readByte()
        stream.readByte()
        if (mapVersion <= 4) {
            stream.readByte()
            stream.readByte()
        } else {
            stream.readUnsignedShort()
            stream.readUnsignedShort()
        }
        stream.readByte()
        stream.readInt()
    }

}