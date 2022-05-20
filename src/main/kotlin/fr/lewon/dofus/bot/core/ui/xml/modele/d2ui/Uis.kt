package fr.lewon.dofus.bot.core.ui.xml.modele.d2ui

import com.fasterxml.jackson.annotation.JsonProperty

data class Uis(
    var group: String = "",
    @field:JsonProperty("ui") var uiList: List<Ui> = emptyList(),
)