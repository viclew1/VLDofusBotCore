package fr.lewon.dofus.bot.core.d2p.maps.element

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.d2p.maps.cell.Cell

abstract class BasicElement(val cell: Cell, var elementType: Int) {

    abstract fun read(mapVersion: Int, stream: ByteArrayReader)

}