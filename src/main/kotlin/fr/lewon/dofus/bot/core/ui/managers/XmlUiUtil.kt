package fr.lewon.dofus.bot.core.ui.managers

import fr.lewon.dofus.bot.core.ui.UIBounds
import fr.lewon.dofus.bot.core.ui.xml.modele.ui.AnchorPoint
import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Anchor
import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Anchors
import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Container
import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Dimension
import org.xml.sax.InputSource
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.sax.SAXSource


object XmlUiUtil {

    private const val CONTAINER_TOKEN = "Container"
    private val CONTAINER_EQUIVALENT_TOKENS = listOf(
        "windowWithoutTitleBar", "window", "TextureBitmap", "Texture", "blockDoubleBorder", "block",
        "Content", "SimpleButton", "Label", "simpleButton", "searchInput", "Grid", "themeIconButton", "ProgressBar",
        "Button", "Common"
    )

    val rootContainerByName = HashMap<String, Container>()

    fun init(xmlFile: File) {
        try {
            val xmlContent = replaceContainerTokens(xmlFile.readText())
            val context = JAXBContext.newInstance(Container::class.java)
            val spf = SAXParserFactory.newInstance()
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            spf.setFeature("http://xml.org/sax/features/validation", false)

            val xmlReader = spf.newSAXParser().xmlReader
            val inputSource = InputSource(xmlContent.byteInputStream())
            val source = SAXSource(xmlReader, inputSource)

            val container = context.createUnmarshaller().unmarshal(source) as Container
            container.parentContainer = UIBounds.GLOBAL_CONTAINER
            initContainers(container, container)
            container.name = xmlFile.name
            rootContainerByName[container.name] = container
        } catch (t: Throwable) {
        }
    }

    private fun replaceContainerTokens(xmlContent: String): String {
        var updatedXmlContent = xmlContent
        for (token in CONTAINER_EQUIVALENT_TOKENS) {
            updatedXmlContent = updatedXmlContent.replace("<$token", "<$CONTAINER_TOKEN")
                .replace("</$token>", "</$CONTAINER_TOKEN>")
        }
        return updatedXmlContent.replace("btnCloseName", "name")
    }

    private fun initContainers(rootContainer: Container, container: Container) {
        flattenChildContainerIfNeeded(container)
        updateAnchorsParameters(container.anchors)
        addMissingAnchors(container)
        container.root = rootContainer
        container.constants = rootContainer.constants
        for (subContainer in container.containers) {
            subContainer.parentContainer = container
            initContainers(rootContainer, subContainer)
        }
    }

    private fun updateAnchorsParameters(anchors: Anchors) {
        var firstWithoutPoint = true
        for (anchor in anchors.anchorList) {
            if (anchor.relativePoint.isEmpty()) {
                anchor.relativePoint = AnchorPoint.TOPLEFT.name
            }
            val anchorPoint = when {
                anchor.point.isNotEmpty() -> AnchorPoint.valueOf(anchor.point)
                firstWithoutPoint -> {
                    firstWithoutPoint = false
                    AnchorPoint.TOPLEFT
                }
                anchor.relativePoint.isNotEmpty() -> AnchorPoint.valueOf(anchor.relativePoint)
                else -> error("Impossible")
            }
            anchor.point = anchorPoint.name
        }
    }

    private fun addMissingAnchors(container: Container) {
        if (container.anchors.anchorList.isEmpty()) {
            addAnchorIfNoneMatchesCondition(container, AnchorPoint.TOPLEFT)
            if (container.sizes.isEmpty()) {
                addAnchorIfNoneMatchesCondition(container, AnchorPoint.TOPRIGHT)
                addAnchorIfNoneMatchesCondition(container, AnchorPoint.BOTTOMRIGHT)
                addAnchorIfNoneMatchesCondition(container, AnchorPoint.BOTTOMLEFT)
            }
        } else if (container.sizes.isEmpty()) {
            addAnchorIfNoneMatchesCondition(container, AnchorPoint.TOPLEFT) {
                it.widthRatio < 1f || it.heightRatio < 1f
            }
            addAnchorIfNoneMatchesCondition(container, AnchorPoint.BOTTOMRIGHT) {
                it.widthRatio > 0f || it.heightRatio > 0f
            }
            addAnchorIfNoneMatchesCondition(container, AnchorPoint.BOTTOMLEFT) {
                it.widthRatio < 1f || it.heightRatio > 0f
            }
            addAnchorIfNoneMatchesCondition(container, AnchorPoint.TOPRIGHT) {
                it.widthRatio > 0f || it.heightRatio < 1f
            }
        }
    }

    private fun addAnchorIfNoneMatchesCondition(
        container: Container,
        anchorPointToAdd: AnchorPoint,
        condition: (AnchorPoint) -> Boolean = { false }
    ) {
        if (container.anchors.anchorList.none { condition(AnchorPoint.valueOf(it.point)) }) {
            val anchor = Anchor(anchorPointToAdd.name, anchorPointToAdd.name, absDimension = Dimension("0", "0"))
            container.anchors.anchorList.add(anchor)
        }
    }

    private fun flattenChildContainerIfNeeded(container: Container) {
        if (container.containers.size != 1) return
        val child = container.containers.first()
        if ((container.anchors.anchorList.isEmpty() || child.anchors.anchorList.isEmpty())
            && container.sizes.isEmpty()
            && (container.name.isEmpty() || child.name.isEmpty())
        ) {
            container.defaultUiPosition = child.defaultUiPosition
            if (container.name.isEmpty()) {
                container.name = child.name
            }
            container.containers = child.containers
            if (child.anchors.anchorList.isNotEmpty()) {
                container.anchors = child.anchors
            }
            container.sizes = child.sizes
            container.containers.forEach { it.parentContainer = container }
            flattenChildContainerIfNeeded(container)
        }
    }

}