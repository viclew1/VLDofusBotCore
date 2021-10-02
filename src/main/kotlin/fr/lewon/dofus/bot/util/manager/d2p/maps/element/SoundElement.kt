package fr.lewon.dofus.bot.util.io.d2p.element

import fr.lewon.dofus.bot.util.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.util.manager.d2p.maps.cell.Cell
import fr.lewon.dofus.bot.util.manager.d2p.maps.element.BasicElement

class SoundElement(cell: Cell) : BasicElement(cell) {

    override fun read(mapVersion: Int, stream: ByteArrayReader) {
        stream.readInt()
        stream.readUnsignedShort()
        stream.readInt()
        stream.readInt()
        stream.readUnsignedShort()
        stream.readUnsignedShort()
    }

}