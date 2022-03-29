package jie.wen.skeduloChallenge.service

import jie.wen.skeduloChallenge.data.Performance

interface OutputPerformanceJsonFileService {
    fun generatePerformanceJsonFile(output: MutableList<Performance>, outputPath: String, outputName: String)
    fun generateJsonString(output: MutableList<Performance>): String
}