package fr.lewon.dofus.bot.util.logs

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ArrayBlockingQueue

object VldbLogger {

    var logSubItemCapacity = 50
    var logs = ArrayBlockingQueue<LogItem>(50)
    var minLogLevel = LogLevel.INFO
    var onLogsChange: () -> Unit = {}

    private fun onLogsChange() {
        onLogsChange.invoke()
    }

    fun clearLogs() {
        synchronized(logs) {
            logs.clear()
            onLogsChange()
        }
    }

    fun closeLog(message: String, parent: LogItem) {
        synchronized(logs) {
            parent.closeLog(message)
            onLogsChange()
        }
    }

    fun appendLog(logItem: LogItem, message: String) {
        synchronized(logs) {
            logItem.message += message
            onLogsChange()
        }
    }

    fun log(message: String, parent: LogItem? = null, logLevel: LogLevel = LogLevel.DEBUG): LogItem {
        synchronized(logs) {
            if (logLevel.level >= minLogLevel.level) {
                val ts = SimpleDateFormat("HH:mm:ss.SSS").format(Date())
                println("$ts - [${logLevel.name}] - $message")
            }
            val newItem = LogItem(message, logLevel)
            if (parent != null) {
                parent.addSubItem(newItem)
            } else if (!logs.offer(newItem)) {
                logs.poll()
                logs.offer(newItem)
            }
            onLogsChange()
            return newItem
        }
    }

    fun trace(str: String, parent: LogItem? = null): LogItem {
        return log(str, parent, LogLevel.TRACE)
    }

    fun debug(str: String, parent: LogItem? = null): LogItem {
        return log(str, parent, LogLevel.DEBUG)
    }

    fun info(str: String, parent: LogItem? = null): LogItem {
        return log(str, parent, LogLevel.INFO)
    }

    fun warn(str: String, parent: LogItem? = null): LogItem {
        return log(str, parent, LogLevel.WARN)
    }

    fun error(str: String, parent: LogItem? = null): LogItem {
        return log(str, parent, LogLevel.ERROR)
    }

}