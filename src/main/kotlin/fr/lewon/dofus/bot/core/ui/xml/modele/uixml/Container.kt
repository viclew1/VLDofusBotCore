package fr.lewon.dofus.bot.core.ui.xml.modele.uixml

import fr.lewon.dofus.bot.core.ui.UIPoint
import fr.lewon.dofus.bot.core.ui.UIPosition
import fr.lewon.dofus.bot.core.ui.xml.modele.ui.AnchorPoint
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Definition")
data class Container(
    @field:XmlAttribute
    var name: String = "",

    @field:XmlElement(name = "Size")
    var sizes: ArrayList<Size> = ArrayList(),

    @field:XmlElement(name = "Anchors")
    var anchors: Anchors = Anchors(),

    @field:XmlElement(name = "Container")
    var containers: ArrayList<Container> = ArrayList(),

    @field:XmlElementWrapper(name = "Constants")
    @field:XmlElement(name = "Constant")
    var constants: ArrayList<Constant> = ArrayList(),
) {

    var defaultUiPosition: UIPosition? = null
    lateinit var root: Container
    lateinit var parentContainer: Container

    @delegate:Transient
    val position: UIPoint by lazy {
        getPosition(AnchorPoint.TOPLEFT)
    }

    @delegate:Transient
    private val fixedSize by lazy {
        parseUiPoint(sizes.firstOrNull()?.absDimension)
    }

    @delegate:Transient
    val size: UIPoint by lazy {
        defaultUiPosition?.size
            ?: fixedSize
            ?: computeSizeWithAnchors()
    }

    @delegate:Transient
    val centerPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.CENTER)
    }

    @delegate:Transient
    val topLeftPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.TOPLEFT)
    }

    @delegate:Transient
    val topPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.TOP)
    }

    @delegate:Transient
    val topRightPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.TOPRIGHT)
    }

    @delegate:Transient
    val rightPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.RIGHT)
    }

    @delegate:Transient
    val bottomRightPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.BOTTOMRIGHT)
    }

    @delegate:Transient
    val bottomPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.BOTTOM)
    }

    @delegate:Transient
    val bottomLeftPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.BOTTOMLEFT)
    }

    @delegate:Transient
    val leftPosition: UIPoint? by lazy {
        getForcedPosition(AnchorPoint.LEFT)
    }

    private fun getForcedPosition(anchorPoint: AnchorPoint): UIPoint? {
        val anchor = getAnchor(anchorPoint) ?: return null
        val anchorSize = parseUiPoint(anchor.absDimension)
            ?: error("Non abs dimension not treated yet")
        val relativeContainer = getRelativeContainer(anchor)
        val relativePoint = if (anchor.relativePoint == "") {
            AnchorPoint.TOPLEFT
        } else AnchorPoint.valueOf(anchor.relativePoint)
        val relativePosition = relativeContainer.getPosition(relativePoint)
        return relativePosition.transpose(anchorSize)
    }

    private fun getAnchor(anchorPoint: AnchorPoint): Anchor? {
        val validAnchors = anchors.anchorList.filter { AnchorPoint.valueOf(it.point) == anchorPoint }
        if (validAnchors.size > 1) {
            error("Two anchor on the same point - $name (${root.name})")
        }
        return validAnchors.firstOrNull()
    }

    private fun getPosition(anchorPoint: AnchorPoint): UIPoint {
        val positionsByAnchorPoint = getNonNullPositionsByAnchorPoint()
        positionsByAnchorPoint[anchorPoint]?.let { return it }
        val refAnchorPointWithPosition = positionsByAnchorPoint.toList().firstOrNull()
            ?: (AnchorPoint.TOPLEFT to UIPoint())
        val refAnchorPoint = refAnchorPointWithPosition.first
        val refPosition = refAnchorPointWithPosition.second
        val widthRatio = refAnchorPoint.widthRatio - anchorPoint.widthRatio
        val heightRatio = refAnchorPoint.heightRatio - anchorPoint.heightRatio
        return UIPoint(refPosition.x - widthRatio * size.x, refPosition.y - heightRatio * size.y)
    }

    private fun computeSizeWithAnchors(): UIPoint {
        val positionsByAnchorPoint = getNonNullPositionsByAnchorPoint()
        val firstAnchorPointWithPosition = positionsByAnchorPoint.toList().firstOrNull()
            ?: return parentContainer.size
        val firstAPoint = firstAnchorPointWithPosition.first
        val firstPosition = firstAnchorPointWithPosition.second
        val secondAnchorPointWithPosition = positionsByAnchorPoint.toList().firstOrNull {
            it.first.widthRatio != firstAPoint.widthRatio && it.first.heightRatio != firstAPoint.heightRatio
        } ?: return parentContainer.size
        val secondAPoint = secondAnchorPointWithPosition.first
        val secondPosition = secondAnchorPointWithPosition.second
        val width = (firstPosition.x - secondPosition.x) * 1f / (firstAPoint.widthRatio - secondAPoint.widthRatio)
        val height = (firstPosition.y - secondPosition.y) * 1f / (firstAPoint.heightRatio - secondAPoint.heightRatio)
        return UIPoint(width, height)
    }

    private fun getNonNullPositionsByAnchorPoint(): Map<AnchorPoint, UIPoint> {
        return listOf(
            AnchorPoint.CENTER to centerPosition,
            AnchorPoint.TOPLEFT to topLeftPosition,
            AnchorPoint.TOP to topPosition,
            AnchorPoint.TOPRIGHT to topRightPosition,
            AnchorPoint.RIGHT to rightPosition,
            AnchorPoint.BOTTOMRIGHT to bottomRightPosition,
            AnchorPoint.BOTTOM to bottomPosition,
            AnchorPoint.BOTTOMLEFT to bottomLeftPosition,
            AnchorPoint.LEFT to leftPosition
        ).mapNotNull { (k, v) -> if (v == null) null else k to v }.toMap()
    }

    private fun getRelativeContainer(anchor: Anchor): Container {
        return if (anchor.relativeTo.isNotEmpty()) {
            parentContainer.findContainer(anchor.relativeTo) ?: error("Missing container : ${anchor.relativeTo}")
        } else parentContainer
    }

    fun findContainer(name: String): Container? {
        if (this.name == name) {
            return this
        }
        for (container in containers) {
            container.findContainer(name)?.let { return it }
        }
        return null
    }

    private fun parseUiPoint(dimension: Dimension?): UIPoint? {
        return dimension?.let {
            val x = it.x.toFloatOrNull() ?: getConstantValue(it.x)?.toFloatOrNull() ?: 0.0f
            val y = it.y.toFloatOrNull() ?: getConstantValue(it.y)?.toFloatOrNull() ?: 0.0f
            UIPoint(x, y)
        }
    }


    private fun getConstantValue(key: String): String? {
        val treatedKey = key.replace("[", "")
            .replace("]", "")
            .replace("$", "")
        return constants.firstOrNull { it.name == treatedKey }
            ?.value
    }
}