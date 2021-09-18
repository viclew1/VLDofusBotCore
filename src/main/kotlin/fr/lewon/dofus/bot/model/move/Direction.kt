package fr.lewon.dofus.bot.model.move

enum class Direction(
    private val directionInt: Int,
    private val reverseDirFetcher: () -> Direction
) {

    LEFT(4, { RIGHT }),
    RIGHT(0, { LEFT }),
    BOTTOM(2, { TOP }),
    TOP(6, { BOTTOM });

    fun getReverseDir(): Direction {
        return reverseDirFetcher.invoke()
    }

    companion object {
        fun fromInt(directionInt: Int): Direction {
            return values().firstOrNull { it.directionInt == directionInt }
                ?: error("Direction [$directionInt] not found")
        }
    }

}