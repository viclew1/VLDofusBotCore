package fr.lewon.dofus.bot.core.io.gamefiles

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object VldbFilesUtil {

    fun getDofusDirectory(): String {
        val gameLocCmdResult: List<String> = execCmd("cmd", "/c", "where Dofus.exe")
        if (gameLocCmdResult.size != 1) {
            throw RuntimeException("Unable to find Dofus.exe, is it in your path?")
        }
        return File(gameLocCmdResult[0]).parentFile.absolutePath
    }

    private fun execCmd(vararg command: String): List<String> {
        val process = ProcessBuilder(*command).start()
        return BufferedReader(InputStreamReader(process.inputStream)).readLines()
    }

    fun getDofusRoamingDirectory(): String {
        val roamingDirPath = System.getProperty("user.home") + "/AppData/Roaming/Dofus/"
        mkdirIfNeeded(roamingDirPath)
        return roamingDirPath
    }

    fun getVldbConfigDirectory(): String {
        val configDirPath = System.getProperty("user.home") + "/.VLDofusBot"
        mkdirIfNeeded(configDirPath)
        return configDirPath
    }

    private fun mkdirIfNeeded(directoryPath: String) {
        val dirFile = File(directoryPath)
        if (!dirFile.exists() || !dirFile.isDirectory) {
            dirFile.mkdir()
        }
    }

}