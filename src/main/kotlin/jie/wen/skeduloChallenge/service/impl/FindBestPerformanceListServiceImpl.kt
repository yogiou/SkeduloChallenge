package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.FindBestPerformanceListService
import jie.wen.skeduloChallenge.utils.PerformanceUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindBestPerformanceListServiceImpl : FindBestPerformanceListService {
    // find the optimal performance list
    override fun findBestPerformanceList(performanceList: MutableList<Performance>): MutableList<Performance> {
        val performanceQueue = PriorityQueue<Performance>()

        // construct the priority queue
        for (performance in performanceList) {
            performanceQueue.offer(performance)
        }

        val result = mutableListOf<Performance>()

        while (performanceQueue.isNotEmpty()) {
            val current = performanceQueue.poll()

            if (result.isNotEmpty() && result.last().finish < current.finish && current.start < result.last().finish) { // update the incoming performance's start time which overlapped last performance
                current.start = result.last().finish
                performanceQueue.offer(current)
            } else {
                if (performanceQueue.isNotEmpty()) {
                    // check if the performance is still ongoing
                    if (PerformanceUtils.isPerformanceValid(result, current)) {
                        val next: Performance? = findNextHigherPriorityPerformance(performanceQueue, current)

                        next?.apply {
                            if (current.finish > next.finish) {
                                // if incoming higher priority perforance is overlapped with current but will finish after the incoming one, split and add it to priority queue
                                val performanceObject = Performance(current.band, current.priority, this.finish, current.finish)
                                performanceQueue.offer(performanceObject)
                            }

                            // update current performance the finish time and go to higher priority performance
                            val performanceToResult = Performance(current.band, current.priority, current.start, this.start)
                            result.add(performanceToResult)
                        } ?: run {
                            if (result.isEmpty() || result.last().finish < current.finish) {
                                result.add(current)
                            }
                        }
                    }
                } else {
                    if (result.isEmpty() || result.last().finish < current.finish) {
                        result.add(current)
                    }
                }
            }
        }

        return result
    }

    // find the next performance which has higher priority and most recent within the period of current performance
    override fun findNextHigherPriorityPerformance(priorityQueue: PriorityQueue<Performance>, current: Performance): Performance? {
        val iterator = priorityQueue.iterator()

        var result: Performance? = null

        while (iterator.hasNext()) {
            val next = iterator.next()
            if (current.priority < next.priority && current.finish > next.start) {
                if (result == null || result.start > next.start) {
                    result = next
                }
            }
        }

        return result
    }
}