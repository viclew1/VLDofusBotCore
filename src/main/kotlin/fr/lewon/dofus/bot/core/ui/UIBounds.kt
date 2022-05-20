package fr.lewon.dofus.bot.core.ui

import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Container

object UIBounds {

    const val TOTAL_WIDTH = 1280f
    const val TOTAL_HEIGHT = 1024f
    val GLOBAL_BOUNDS_UI_POSITION = UIPosition(UIPoint(), UIPoint(TOTAL_WIDTH, TOTAL_HEIGHT))
    val GLOBAL_CONTAINER = Container("ROOT").also { it.defaultUiPosition = GLOBAL_BOUNDS_UI_POSITION }
    val CENTER = UIPoint(TOTAL_WIDTH / 2f, TOTAL_HEIGHT / 2f)

}