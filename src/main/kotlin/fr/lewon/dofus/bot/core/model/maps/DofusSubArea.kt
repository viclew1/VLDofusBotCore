package fr.lewon.dofus.bot.core.model.maps

data class DofusSubArea(
    val id: Double,
    val worldMapId: Int,
    val packId: Int,
    val isConquestVillage: Boolean,
    val associatedZaapMapId: Double,
    val name: String,
    val area: DofusArea
)