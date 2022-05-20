package fr.lewon.dofus.bot.core.ui.xml.modele.d2ui

data class Header(
    var name: String = "",
    var version: String = "",
    var dofusVersion: String = "",
    var author: String = "",
    var shortDescription: String = "",
    var description: String = "",
    var priority: Int = 0
)