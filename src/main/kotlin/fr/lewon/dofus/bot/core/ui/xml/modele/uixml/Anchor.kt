package fr.lewon.dofus.bot.core.ui.xml.modele.uixml

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class Anchor(
    @field:XmlAttribute var point: String = "",
    @field:XmlAttribute var relativePoint: String = "",
    @field:XmlAttribute var relativeTo: String = "",
    @field:XmlElement(name = "RelDimension") var relDimension: Dimension = Dimension(),
    @field:XmlElement(name = "AbsDimension") var absDimension: Dimension = Dimension()
)