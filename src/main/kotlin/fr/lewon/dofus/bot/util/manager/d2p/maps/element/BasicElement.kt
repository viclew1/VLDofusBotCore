package fr.lewon.dofus.bot.util.manager.d2p.maps.element

import fr.lewon.dofus.bot.util.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.util.manager.d2p.maps.cell.Cell

abstract class BasicElement(val cell: Cell) {

    abstract fun read(mapVersion: Int, stream: ByteArrayReader)

}