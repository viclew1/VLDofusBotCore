package fr.lewon.dofus.bot.core.ui.managers

import fr.lewon.dofus.bot.core.ui.xml.modele.ui.UiData
import fr.lewon.dofus.bot.core.ui.xml.modele.uixml.Container

object UiDataManager {

    private val uiDataList = ArrayList<UiData>()

    fun load(uiData: UiData) {
        uiDataList.add(uiData)
    }

    fun getContainer(key: String, ctr: String): Container {
        val rootContainer = findRootContainer(key, ctr)
        if (rootContainer.containers.isEmpty()) {
            error("No container in UiDefinition : $key $ctr")
        }
        return rootContainer
    }

    private fun findRootContainer(key: String, ctr: String): Container {
        val rootContainerByKey = uiDataList.firstOrNull { it.name == key }
            ?.let { XmlUiUtil.rootContainerByName[it.file] }
        return rootContainerByKey?.findContainer(ctr)
            ?: XmlUiUtil.rootContainerByName["$ctr.xml"]
            ?: rootContainerByKey
            ?: error("Couldn't find root container ($key $ctr)")
    }

}