package jie.wen.skeduloChallenge.service.impl

import com.google.gson.GsonBuilder
import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.OutputPerformanceJsonFileService
import org.springframework.stereotype.Service
import java.io.File

@Service
class OutputPerformanceJsonFileServiceImpl: OutputPerformanceJsonFileService {
    // generate the output json file
    override fun generatePerformanceJsonFile(output: MutableList<Performance>, outputPath: String, outputName: String) {
        val file = File(outputPath + File.separator + outputName)
        file.writeText(generateJsonString(output))
    }

    // generate the json string by Gson
    override fun generateJsonString(output: MutableList<Performance>): String {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
        return gson.toJson(output)
    }
}