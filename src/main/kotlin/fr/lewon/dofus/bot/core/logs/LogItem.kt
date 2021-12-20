package fr.lewon.dofus.bot.core.logs

import java.util.concurrent.ArrayBlockingQueue

class LogItem(var message: String, val logLevel: LogLevel, subItemCapacity: Int) {

    private val subLogs = ArrayBlockingQueue<LogItem>(subItemCapacity)
    private var closeMessage: String? = null

    fun addSubItem(logItem: LogItem) {
        synchronized(subLogs) {
            if (!subLogs.offer(logItem)) {
                subLogs.poll()
                subLogs.offer(logItem)
            }
        }
    }

    override fun toString(): String {
        return displayLog(0)
    }

    private fun displayLog(depth: Int): String {
        var prefix = ""
        for (i in 0 until depth) {
            prefix += " - "
        }
        var ret = prefix + message
        for (subLog in subLogs) {
            ret += "\n" + subLog.displayLog(depth + 1)
        }
        if (closeMessage != null) {
            ret += if (subLogs.isEmpty()) {
                " $closeMessage"
            } else {
                "\n$prefix$closeMessage"
            }
        }
        return ret
    }

    fun closeLog(message: String) {
        closeMessage = message
    }
}