package fr.lewon.dofus.bot.core.ui.xml.modele.d2ui

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.dofus.bot.core.ui.xml.modele.ui.UiGroup

data class Module(
    var header: Header = Header(),
    @field:JsonProperty("uiGroup") var uiGroupList: List<UiGroup> = emptyList(),
    @field:JsonProperty("uis") var uisList: List<Uis> = emptyList(),
    var script: String = ""
)