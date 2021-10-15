package fr.lewon.dofus.bot.core.logs

interface VldbLoggerListener {

    fun onLogsChange(logs: List<LogItem>)

}