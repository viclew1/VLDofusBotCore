package fr.lewon.dofus.bot.core.d2p.maps.cell

data class CellData(
    val cellId: Int,
    var id: Int = 0,
    var speed: Int = 0,
    var mapChangeData: Int = 0,
    var moveZone: Int = 0,
    var losmov: Int = 0,
    var floor: Int = 0,
    var arrow: Int = 0,
    var linkedZone: Int = 0,
    var mov: Boolean = false,
    var los: Boolean = false,
    var nonWalkableDuringFight: Boolean = false,
    var red: Boolean = false,
    var blue: Boolean = false,
    var farmCell: Boolean = false,
    var havenbagCell: Boolean = false,
    var visible: Boolean = false,
    var nonWalkableDuringRP: Boolean = false
) {

    fun hasLinkedZoneRP(): Boolean {
        return this.mov && !this.farmCell
    }

    fun getLinkedZoneRP(): Int {
        return this.linkedZone and 240 shr 4
    }

    fun hasLinkedZoneFight(): Boolean {
        return this.mov && !this.nonWalkableDuringFight && !this.farmCell && !this.havenbagCell
    }

    fun getLinkedZoneFight(): Int {
        return this.linkedZone and 15
    }

}