package fr.lewon.dofus.bot.core.manager.d2p.maps.element

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.manager.d2p.maps.cell.Cell


class GraphicalElement(cell: Cell) : BasicElement(cell, ElementType.GRAPHICAL.typeValue) {

    var elementId = -1
    var identifier = -1

    override fun read(mapVersion: Int, stream: ByteArrayReader) {
        elementId = stream.readInt()
        stream.skip(6)
        if (mapVersion <= 4) {
            stream.skip(2)
        } else {
            stream.skip(4)
        }
        stream.skip(1)
        identifier = stream.readInt()
    }

}