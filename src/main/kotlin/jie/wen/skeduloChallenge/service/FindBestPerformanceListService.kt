package jie.wen.skeduloChallenge.service

import jie.wen.skeduloChallenge.data.Performance
import java.util.*

interface FindBestPerformanceListService {
    fun findBestPerformanceList(performanceList: MutableList<Performance>): MutableList<Performance>
    fun findNextHigherPriorityPerformance(priorityQueue: PriorityQueue<Performance>, current: Performance): Performance?
}