package jie.wen.skeduloChallenge.service

import jie.wen.skeduloChallenge.data.Performance

interface ReadInputJsonFileService {
    fun readJsonFile(path: String): MutableList<Performance>
    fun parseJson(json: String): MutableList<Performance>
}