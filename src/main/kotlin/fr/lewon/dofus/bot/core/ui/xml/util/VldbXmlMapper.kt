package fr.lewon.dofus.bot.core.ui.xml.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper

class VldbXmlMapper : XmlMapper(buildModule()) {
    companion object {
        fun buildModule(): JacksonXmlModule {
            val module = JacksonXmlModule()
            module.setDefaultUseWrapper(false)
            return module
        }
    }

    init {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}