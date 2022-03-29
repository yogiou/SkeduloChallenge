package jie.wen.skeduloChallenge.service.impl

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.ReadInputJsonFileService
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException

@Service
class ReadInputJsonFileServiceImpl : ReadInputJsonFileService {
    // read the input file
    override fun readJsonFile(path: String): MutableList<Performance> {

        val jsonFile = File(path)

        if (jsonFile.exists()) {
            val json = jsonFile.readText(Charsets.UTF_8)
            return parseJson(json)
        }

        throw FileNotFoundException()
    }

    // parse the json string to json object by Gson
    override fun parseJson(json: String): MutableList<Performance> {
        val gson = GsonBuilder().create()
        return gson.fromJson(json, object: TypeToken<List<Performance>>() {}.type)
    }
}