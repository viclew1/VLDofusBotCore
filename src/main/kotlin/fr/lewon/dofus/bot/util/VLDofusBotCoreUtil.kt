package fr.lewon.dofus.bot.util

import fr.lewon.dofus.bot.util.manager.VldbManager
import org.reflections.Reflections

object VLDofusBotCoreUtil {

    fun initAll() {
        Reflections(VldbManager::class.java.packageName)
            .getSubTypesOf(VldbManager::class.java)
            .mapNotNull { it.kotlin.objectInstance }
            .forEach { it.initManager() }
    }

}