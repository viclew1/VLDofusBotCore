package fr.lewon.dofus.bot.core.logs

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ArrayBlockingQueue

class VldbLogger(
    private val minLogLevel: LogLevel = LogLevel.INFO,
    logItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY
) {

    companion object {
        const val DEFAULT_LOG_ITEM_CAPACITY = 8
    }

    private val logs = ArrayBlockingQueue<LogItem>(logItemCapacity)
    val listeners = ArrayList<VldbLoggerListener>()

    private fun onLogsChange() {
        val filteredLogs = logs.filter { it.logLevel.level >= minLogLevel.level }
        listeners.forEach { it.onLogsChange(filteredLogs) }
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

    fun addSubLog(message: String, parent: LogItem, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        val newItem = LogItem(message, parent.logLevel, subItemCapacity)
        parent.addSubItem(newItem)
        onLogsChange()
        return newItem
    }

    fun log(
        message: String,
        logLevel: LogLevel = LogLevel.DEBUG,
        subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY
    ): LogItem {
        synchronized(logs) {
            val newItem = LogItem(message, logLevel, subItemCapacity)
            if (logLevel.level >= minLogLevel.level) {
                val ts = SimpleDateFormat("HH:mm:ss.SSS").format(Date())
                //println("$ts - [${logLevel.name}] - $message")
                if (!logs.offer(newItem)) {
                    logs.poll()
                    logs.offer(newItem)
                    onLogsChange()
                }
            }
            return newItem
        }
    }

    fun trace(str: String, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        return log(str, LogLevel.TRACE, subItemCapacity)
    }

    fun debug(str: String, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        return log(str, LogLevel.DEBUG, subItemCapacity)
    }

    fun info(str: String, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        return log(str, LogLevel.INFO, subItemCapacity)
    }

    fun warn(str: String, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        return log(str, LogLevel.WARN, subItemCapacity)
    }

    fun error(str: String, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        return log(str, LogLevel.ERROR, subItemCapacity)
    }

}