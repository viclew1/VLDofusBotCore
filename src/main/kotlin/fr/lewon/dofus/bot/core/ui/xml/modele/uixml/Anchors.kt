package fr.lewon.dofus.bot.core.ui.xml.modele.uixml

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class Anchors(
    @field:XmlElement(name = "Anchor") var anchorList: ArrayList<Anchor> = ArrayList()
)