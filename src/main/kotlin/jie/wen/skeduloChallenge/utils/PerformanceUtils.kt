package jie.wen.skeduloChallenge.utils

import jie.wen.skeduloChallenge.data.Performance

object PerformanceUtils {
    fun isPerformanceValid(list: MutableList<Performance>, performanceToAdd: Performance): Boolean {
        return list.isEmpty() || list.last().finish < performanceToAdd.finish
    }

    fun show(performanceList: MutableList<Performance>) {
        for (performance in performanceList) {
            println("${performance.band} ${TimeUtils.printTime(performance.start)} ${TimeUtils.printTime(performance.finish)} ${performance.priority}")
        }

        println()
    }
}