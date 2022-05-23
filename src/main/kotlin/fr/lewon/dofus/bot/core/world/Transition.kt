package fr.lewon.dofus.bot.core.world

data class Transition(
    val edge: Edge,
    val direction: Int,
    val type: TransitionType,
    val skillId: Int,
    var criterion: String,
    val transitionMapId: Double,
    val cellId: Int,
    val id: Double
)