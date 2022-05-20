package fr.lewon.dofus.bot.core.ui.xml.modele.uixml

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class Size(
    @field:XmlElement(name = "RelDimension") var relDimension: Dimension = Dimension(),
    @field:XmlElement(name = "AbsDimension") var absDimension: Dimension = Dimension()
)