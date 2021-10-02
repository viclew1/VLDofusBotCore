package fr.lewon.dofus.bot.util.manager.d2o

import fr.lewon.dofus.bot.util.io.gamefiles.VldbFilesUtil
import fr.lewon.dofus.bot.util.io.stream.ByteArrayReader
import fr.lewon.dofus.bot.util.manager.VldbManager
import java.io.File

object CharacteristicManager : VldbManager {

    private lateinit var characteristicIdByKeyword: HashMap<String, Int>

    override fun initManager() {
        characteristicIdByKeyword = HashMap()
        val dataDirPath = "${VldbFilesUtil.getDofusDirectory()}/data/"
        val d2oMapFile = File("$dataDirPath/common/Characteristics.d2o")
        val stream = ByteArrayReader(d2oMapFile.readBytes())
        require("D2O" == stream.readString(3)) { error("Invalid D2O file") }
        stream.readInt()
        while (stream.readInt() == 1) {
            val id = stream.readInt()
            val keyWord = stream.readUTF()
            characteristicIdByKeyword[keyWord] = id
            stream.readInt()
            stream.readUTF()
            stream.readInt()
            stream.readBoolean()
            stream.readInt()
            stream.readInt()
            stream.readBoolean()
        }
    }

    fun getCharacteristicId(keyWord: String): Int {
        return characteristicIdByKeyword[keyWord] ?: error("Characteristic [$keyWord] not found")
    }

}