package fr.lewon.dofus.bot.core.manager.d2p.maps.element

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.manager.d2p.maps.cell.Cell

class SoundElement(cell: Cell) : BasicElement(cell, ElementType.SOUND.typeValue) {

    override fun read(mapVersion: Int, stream: ByteArrayReader) {
        stream.readInt()
        stream.readUnsignedShort()
        stream.readInt()
        stream.readInt()
        stream.readUnsignedShort()
        stream.readUnsignedShort()
    }

}