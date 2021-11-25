package fr.lewon.dofus.bot.core.manager.world

data class Edge(
    val from: Vertex,
    val to: Vertex,
    val transitions: ArrayList<Transition> = ArrayList()
)