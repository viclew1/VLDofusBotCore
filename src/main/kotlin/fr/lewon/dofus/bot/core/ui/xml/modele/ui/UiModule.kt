package fr.lewon.dofus.bot.core.ui.xml.modele.ui

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.core.ui.managers.UiDataManager
import fr.lewon.dofus.bot.core.ui.xml.modele.d2ui.Module
import fr.lewon.dofus.bot.core.ui.xml.util.VldbXmlMapper


class UiModule(private val module: Module) {

    companion object {
        fun init(input: ByteArrayReader): UiModule {
            val headerStr = input.readUTF()
            if (headerStr != "D2UI") {
                error("Malformed ui data file")
            }
            val module = VldbXmlMapper().readValue(input.readUTF(), Module::class.java)
            val instance = UiModule(module)
            instance.fillFromXml()
            return instance
        }
    }

    private fun fillFromXml() {
        for (uis in module.uisList) {
            val uisGroup = uis.group
            for (ui in uis.uiList) {
                val file = ui.file.replace("::", "/")
                    .replace("\\", "/")
                    .split("/")
                    .lastOrNull()
                    ?: error("No file associated to UI : $ui (${module.header})")
                val uiData = UiData(this, ui.name, file, ui.clazz, uisGroup)
                UiDataManager.load(uiData)
            }
        }
    }

}