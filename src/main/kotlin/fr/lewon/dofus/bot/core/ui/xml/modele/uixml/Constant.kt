package fr.lewon.dofus.bot.core.ui.xml.modele.uixml

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute

@XmlAccessorType(XmlAccessType.FIELD)
data class Constant(
    @field:XmlAttribute var name: String = "",
    @field:XmlAttribute var value: String = ""
)