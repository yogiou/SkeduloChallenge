package jie.wen.skeduloChallenge.service

import jie.wen.skeduloChallenge.data.Performance

interface FindBestPerformanceListService {
    fun findBestPerformanceList(performanceList: MutableList<Performance>): MutableList<Performance>
    fun addPerformance(performanceList: MutableList<Performance>, current: Performance)
    fun show(performanceList: MutableList<Performance>)
    fun findNextPerformance(list: MutableList<Performance>, current: Performance): Performance?
}