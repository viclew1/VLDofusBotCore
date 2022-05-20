package fr.lewon.dofus.bot.core.ui.xml.modele.d2ui

import com.fasterxml.jackson.annotation.JsonProperty

data class Ui(
    var name: String = "",
    var file: String = "",
    @field:JsonProperty("class") var clazz: String = ""
)