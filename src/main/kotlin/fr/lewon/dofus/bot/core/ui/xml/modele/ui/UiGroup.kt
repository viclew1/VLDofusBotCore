package fr.lewon.dofus.bot.core.ui.xml.modele.ui

data class UiGroup(
    val name: String = "",
    val exclusive: Boolean = false,
    val permanent: Boolean = false,
    var uisNames: List<String> = emptyList()
)