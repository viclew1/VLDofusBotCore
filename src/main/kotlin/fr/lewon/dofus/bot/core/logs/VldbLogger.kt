package fr.lewon.dofus.bot.core.logs

import java.util.*
import java.util.concurrent.ArrayBlockingQueue

class VldbLogger(logItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY) {

    companion object {
        const val DEFAULT_LOG_ITEM_CAPACITY = 8
    }

    private val logs = ArrayBlockingQueue<LogItem>(logItemCapacity)
    val listeners = ArrayList<VldbLoggerListener>()

    private fun onLogsChange() {
        val logsCopy = logs.toList()
        listeners.forEach { it.onLogsChange(logsCopy) }
    }

    fun clearLogs() {
        synchronized(logs) {
            logs.clear()
            onLogsChange()
        }
    }

    fun closeLog(message: String, parent: LogItem, clearSubLogs: Boolean = false) {
        synchronized(logs) {
            parent.closeLog(message)
            if (clearSubLogs) {
                parent.clearSubLogs()
            }
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
        val newItem = LogItem(message, subItemCapacity)
        parent.addSubItem(newItem)
        onLogsChange()
        return newItem
    }

    fun log(message: String, subItemCapacity: Int = DEFAULT_LOG_ITEM_CAPACITY): LogItem {
        synchronized(logs) {
            val newItem = LogItem(message, subItemCapacity)
            if (!logs.offer(newItem)) {
                logs.poll()
                logs.offer(newItem)
            }
            onLogsChange()
            return newItem
        }
    }

}