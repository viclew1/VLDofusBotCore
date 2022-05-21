package fr.lewon.dofus.bot.core.model.maps


data class DofusMap(
    val subArea: DofusSubArea,
    val worldMap: DofusWorldMap?,
    val id: Double,
    val posX: Int,
    val posY: Int,
    val outdoor: Boolean,
    val isTransition: Boolean,
    val hasPriorityOnWorldMap: Boolean
) {

    fun getCoordinates(): DofusCoordinates {
        return DofusCoordinates(posX, posY)
    }

}