package fr.lewon.dofus.bot.core.manager

interface VldbManager {

    fun initManager()

    fun getNeededManagers(): List<VldbManager>

}