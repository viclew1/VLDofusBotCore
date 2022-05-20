package fr.lewon.dofus.bot.core.ui.xml.modele.ui

import fr.lewon.dofus.bot.core.ui.xml.modele.d2ui.Module

data class UiData(
    var module: UiModule = UiModule(Module()),
    var name: String = "",
    var file: String = "",
    var uiClassName: String = "",
    var uiGroupName: String = "",
)