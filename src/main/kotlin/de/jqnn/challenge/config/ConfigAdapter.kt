package de.jqnn.challenge.config

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import java.io.BufferedWriter
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class ConfigAdapter(path: String, configName: String) {

    private var configurationFile: File? = null
    private var valueMap = LinkedHashMap<String, Any>()

    private val gson = GsonBuilder().setPrettyPrinting().create()

    init {
        val directory = File("plugins/Challenges/$path")
        this.configurationFile = File("plugins/Challenges/$path/$configName")

        if (!directory.exists())
            directory.mkdir()

        if (!this.configurationFile?.exists()!!)
            this.configurationFile?.createNewFile()
    }

    fun set(key: String, value: Any) {
        this.valueMap[key] = value
    }

    fun setList(key: String, list: List<String>) {
        this.valueMap[key] = list
    }

    fun setObjectList(key: String, list: List<Any>) {
        this.valueMap[key] = list
    }

    fun getObjectList(key: String): Any? {
        return this.valueMap[key]
    }

    operator fun get(key: String): Any? {
        return this.valueMap[key]
    }

    fun getBoolean(key: String): Boolean {
        return this[key] as Boolean
    }

    fun getString(key: String): String {
        return this[key] as String
    }

    fun getInt(key: String): Int {
        return (this[key] as Double).toInt()
    }

    fun getList(key: String): Any? {
        return this.valueMap[key]
    }

    fun load() {
        val inputStream = Files.newInputStream(configurationFile!!.toPath())
        val reader = JsonReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        this.valueMap = this.gson.fromJson(reader, LinkedHashMap::class.java) ?: LinkedHashMap()
    }

    fun save() {
        val writer = BufferedWriter(
            OutputStreamWriter(
                Files.newOutputStream(configurationFile!!.toPath()),
                StandardCharsets.UTF_8
            )
        )
        writer.write(this.gson.toJson(this.valueMap))
        writer.close()
    }

    fun existsKey(key: String): Boolean {
        return this.valueMap.containsKey(key)
    }
}