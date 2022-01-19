package fr.lewon.dofus.bot.core.manager.world

data class Transition(
    val edge: Edge,
    val direction: Int,
    val type: TransitionType,
    val skillId: Int,
    val criterion: String,
    val transitionMapId: Double,
    val cellId: Int,
    val id: Double
)