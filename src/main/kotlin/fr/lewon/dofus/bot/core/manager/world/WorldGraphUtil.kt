package fr.lewon.dofus.bot.core.manager.world

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.model.maps.DofusMap
import java.util.*

object WorldGraphUtil {

    private val vertices = HashMap<Double, HashMap<Int, Vertex>>()
    private val edges = HashMap<Double, HashMap<Double, Edge>>()
    private val outgoingEdges = HashMap<Double, ArrayList<Edge>>()

    private var vertexUid = 0.0

    fun init(stream: ByteArrayReader) {
        val edgeCount = stream.readInt()
        for (i in 0 until edgeCount) {
            val from = addVertex(stream.readDouble(), stream.readInt())
            val dest = addVertex(stream.readDouble(), stream.readInt())
            val edge = addEdge(from, dest)
            val transitionCount = stream.readInt()
            for (j in 0 until transitionCount) {
                val transition = Transition(
                    stream.readUnsignedByte(),
                    TransitionType.fromInt(stream.readUnsignedByte()),
                    stream.readInt(),
                    stream.readString(stream.readInt()),
                    stream.readDouble(),
                    stream.readInt(),
                    stream.readDouble()
                )
                edge.transitions.add(transition)
            }
        }
    }

    private fun addEdge(from: Vertex, dest: Vertex): Edge {
        getEdge(from, dest)?.let { return it }
        val edge = Edge(from, dest)
        val fromEdges = edges.computeIfAbsent(from.uid) { HashMap() }
        fromEdges[dest.uid] = edge
        val outgoing = outgoingEdges.computeIfAbsent(from.uid) { ArrayList() }
        outgoing.add(edge)
        return edge
    }

    private fun getEdge(from: Vertex, dest: Vertex): Edge? {
        return edges[from.uid]?.get(dest.uid)
    }

    private fun addVertex(mapId: Double, zone: Int): Vertex {
        val mapVertices = vertices.computeIfAbsent(mapId) { HashMap() }
        return mapVertices.computeIfAbsent(zone) { Vertex(mapId, zone, vertexUid++) }
    }

    fun getPath(fromMap: DofusMap, fromZone: Int, toMaps: List<DofusMap>): List<Transition>? {
        val fromVertex = vertices[fromMap.id]?.get(fromZone) ?: return null
        val toMapsIds = toMaps.map { it.id }
        var destVertices = vertices.entries
            .filter { toMapsIds.contains(it.key) }
            .flatMap { it.value.values }
        if (destVertices.isEmpty()) {
            return null
        }
        destVertices.filter { it.zoneId == 1 }.takeIf { it.isNotEmpty() }?.let {
            destVertices = it
        }
        val explored = ArrayList<Vertex>()
        var frontier = ArrayList<Node>()
        val initialNode = Node(null, fromVertex, null)
        explored.add(fromVertex)
        frontier.add(initialNode)
        while (frontier.isNotEmpty()) {
            val newFrontier = ArrayList<Node>()
            for (node in frontier) {
                if (destVertices.contains(node.vertex)) {
                    return node.getTransitions()
                }
                val mapOutgoingEdges = outgoingEdges[node.vertex.uid]
                    ?.filter { !explored.contains(it.to) }
                    ?.flatMap { buildNodes(node, it) }
                    ?.onEach { explored.add(it.vertex) }
                    ?: emptyList()
                newFrontier.addAll(mapOutgoingEdges)
            }
            frontier = newFrontier
        }
        return null
    }

    private fun buildNodes(parentNode: Node, edge: Edge): List<Node> {
        return edge.transitions.filter { it.criterion.isEmpty() }.map { Node(parentNode, edge.to, it) }
    }

    private class Node(val parent: Node?, val vertex: Vertex, val transition: Transition?) {
        fun getTransitions(): List<Transition> {
            val transitions = LinkedList<Transition>()
            transitions.addFirst(transition)
            var parentNode = parent
            while (parentNode?.transition != null) {
                transitions.addFirst(parentNode.transition)
                parentNode = parentNode.parent
            }
            return transitions
        }
    }

}