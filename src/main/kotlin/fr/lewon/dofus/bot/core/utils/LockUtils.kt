package fr.lewon.dofus.bot.core.utils

import java.util.concurrent.locks.ReentrantLock

object LockUtils {

    fun executeThreadedSyncOperation(lock: ReentrantLock, operation: () -> Unit) {
        try {
            lock.lockInterruptibly()
            val condition = lock.newCondition()
            Thread {
                executeSyncOperation(lock) {
                    condition.signal()
                    operation()
                }
            }.start()
            condition.await()
        } finally {
            lock.unlock()
        }
    }

    fun <T> executeSyncOperation(lock: ReentrantLock, operation: () -> T): T {
        try {
            lock.lockInterruptibly()
            return operation()
        } finally {
            lock.unlock()
        }
    }

}