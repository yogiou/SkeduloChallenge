package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.FindBestPerformanceListService
import jie.wen.skeduloChallenge.utils.PerformanceUtils
import org.springframework.stereotype.Service

@Service
class FindBestPerformanceListServiceImpl : FindBestPerformanceListService {
    // find the optimal performance list
    override fun findBestPerformanceList(performanceList: MutableList<Performance>): MutableList<Performance> {
        performanceList.sort()
        val result = mutableListOf<Performance>()

        // iterate the performance list
        while (performanceList.isNotEmpty()) {
            val current = performanceList.removeAt(0)

            // update the incoming performance's start time which overlapped with last performance
            result.takeIf { it.isNotEmpty() && it.last().finish < current.finish && current.start < it.last().finish }?.let {
                val performance = Performance(current.band, current.priority, result.last().finish, current.finish)
                addPerformance(performanceList, performance)
            } ?: run {
                performanceList.takeIf { it.isNotEmpty() }?.let {
                    result.takeIf { PerformanceUtils.isPerformanceValid(it, current) }?.let {
                        // get the next most recent performance with higher priority which the start time is in the period of the current performance
                        val next: Performance? = findNextPerformance(performanceList, current)

                        next?.apply {
                            current.takeIf { it.finish > next.finish }?.let {
                                // if incoming higher priority performance is overlapped with current but will finish after the incoming one, split and add it to priority queue
                                val performanceObject = Performance(current.band, current.priority, this.finish, current.finish)
                                addPerformance(performanceList, performanceObject)
                            }

                            // update current performance the finish time and go to higher priority performance
                            val performanceToResult = Performance(current.band, current.priority, current.start, this.start)
                            result.add(performanceToResult)
                        } ?: result.takeIf { it.isEmpty() || it.last().finish < current.finish }?.let {
                            result.add(current)
                        }
                    }
                } ?: result.takeIf { it.isEmpty() || it.last().finish < current.finish }?.let {
                    result.add(current)
                }
            }
        }

        return result
    }

    // add the performance to the sorted performances list (find the position to add via binary search)
    override fun addPerformance(performanceList: MutableList<Performance>, current: Performance) {
        if (performanceList.isEmpty()) {
            performanceList.add(current)
            return
        }

        var start = 0
        var end = performanceList.size - 1
        var mid: Int

        var foundStart = -1
        var foundEnd = -1

        var subMid: Int = -1

        var s = -1
        var e = -1

        while (start <= end) {
            var run = true

            mid = (end - start) / 2 + start

            if (subMid == -1) {
                subMid = mid
            }

            if (s == -1) {
                s = start
            }

            if (e == -1) {
                e = end
            }

            when {
                sameStartTime(current, mid, performanceList) -> {
                    when {
                        foundEnd == -1 -> {
                            while (s <= e && run) {
                                subMid = (e - s) / 2 + s

                                performanceList.takeIf { subMid == it.size - 1 || current.start < it[subMid + 1].start }?.let {
                                    foundEnd = subMid

                                    subMid = -1
                                    s = -1
                                    e = -1
                                    run = false
                                } ?: run {
                                    s = subMid + 1
                                }
                            }
                        }
                        foundStart == -1 -> {
                            while (s <= e && run) {
                                subMid = (e - s) / 2 + s

                                performanceList.takeIf {subMid == 0 || current.start > it[subMid - 1].start}?.let {
                                    foundStart = subMid
                                    run = false
                                } ?: run {
                                    e = subMid - 1
                                }
                            }
                        }
                        else -> break
                    }
                }

                foundPerformanceToAddByStartTime(mid, current, performanceList) -> {
                    performanceList.add(mid, current)
                    return
                }

                toStartPos(mid, 0) -> {
                    performanceList[mid].takeIf { current.start < it.start }?.let {
                        performanceList.add(mid, current)
                        return
                    } ?: run {
                        start.takeIf { it == end }?.let {
                            performanceList.add(mid + 1, current)
                            return
                        } ?: run {
                            start = mid + 1
                        }
                    }
                }

                toEndPos(mid, performanceList.size - 1) -> {
                    performanceList[mid].takeIf { current.start < it.start }?.let {
                        start.takeIf { it == end }?.let {
                            performanceList.add(mid , current)
                            return
                        } ?: run {
                            end = mid - 1
                        }
                    } ?: run {
                        performanceList.add(mid + 1, current)
                        return
                    }
                }

                current.start > performanceList[mid].start -> start = mid + 1

                else -> end = mid - 1
            }
        }

        start = foundStart
        end = foundEnd
        while (start in 0..end) {
            mid = (end - start) / 2 + start

            when {
                toStartPos(mid, foundStart) -> {
                    performanceList[mid].takeIf { current.priority >= it.priority }?.let {
                        performanceList.add(mid, current)
                    } ?: run {
                        performanceList.add(mid + 1, current)
                    }

                    return
                }

                toEndPos(mid, foundEnd) -> {
                    performanceList[mid].takeIf { current.priority >= it.priority }?.let {
                        performanceList.add(mid, current)
                    } ?: run {
                        performanceList.add(mid + 1, current)
                    }

                    return
                }

                foundPerformanceToAddByPriority(mid, current, performanceList) -> {
                    performanceList.add(mid, current)
                    return
                }

                lowerPriorityThanTarget(mid, current, performanceList) -> end = mid - 1
                else -> start = mid + 1
            }
        }
    }

    private fun sameStartTime(current: Performance, found: Int, performanceList: MutableList<Performance>): Boolean {
        return current.start == performanceList[found].start
    }

    private fun foundPerformanceToAddByStartTime(found: Int, current: Performance, performanceList: MutableList<Performance>): Boolean {
        return found > 0 && current.start < performanceList[found].start && current.start > performanceList[found - 1].start
    }

    private fun foundPerformanceToAddByPriority(found: Int, current: Performance, performanceList: MutableList<Performance>): Boolean {
        return performanceList[found - 1].priority >= current.priority && performanceList[found].priority <= current.priority
    }

    private fun lowerPriorityThanTarget(found: Int, current: Performance, performanceList: MutableList<Performance>): Boolean {
        return performanceList[found].priority < current.priority
    }

    // find the next performance with higher priority within current performance period (search by binary search)
    override fun findNextPerformance(list: MutableList<Performance>, current: Performance): Performance? {
        var performanceList = list.toMutableList()
        var result: Performance? = null
        var start = 0
        var end = performanceList.size - 1
        var mid: Int
        var run = true

        while (start <= end && run) {
            mid = (end - start) / 2 + start

            when {
                toStartPos(mid, 0) -> {
                    performanceList[mid].takeIf { it.start >= current.start && it.start <= current.finish && current.priority < it.priority }?.let {
                        result = performanceList[mid]
                        run = false
                    } ?: run {
                        performanceList = performanceList.subList(1, performanceList.size)
                        start = 0
                        end = performanceList.size - 1
                    }
                }

                toEndPos(mid, performanceList.size - 1) -> {
                    performanceList[mid].takeIf { it.start >= current.start && it.start <= current.finish && current.priority < it.priority}?.let {
                        result = performanceList[mid]
                    }

                    break
                }

                foundPerformance(current, mid, performanceList) -> {
                    performanceList[mid].takeIf { it.start >= current.start && it.start <= current.finish }?.let {
                        result = performanceList[mid]
                    }

                    break
                }

                sameAsPreviousPerformanceStart(mid, performanceList) -> {
                    start = mid + 1
                }

                lowerPriorityThenTargetPerformance(current, mid, performanceList) -> {
                    performanceList = performanceList.subList(mid, performanceList.size)
                    start = 0
                    end = performanceList.size - 1
                }

                else -> end = mid - 1
            }
        }

        return result
    }

    private fun foundPerformance(current: Performance, foundPos: Int, performanceList: MutableList<Performance>): Boolean {
        return current.priority < performanceList[foundPos].priority
                && performanceList[foundPos - 1].start == performanceList[0].start
                && performanceList[foundPos].start > performanceList[foundPos - 1].start
                && performanceList[foundPos].start <= performanceList[foundPos + 1].start
                && performanceList[0].priority <= current.priority
    }

    private fun toStartPos(foundPos: Int, start: Int): Boolean {
        return foundPos == start
    }

    private fun toEndPos(foundPos: Int, end: Int): Boolean {
        return foundPos == end
    }

    private fun sameAsPreviousPerformanceStart(foundPos: Int, performanceList: MutableList<Performance>): Boolean {
        return performanceList[foundPos - 1].start == performanceList[foundPos].start
    }

    private fun lowerPriorityThenTargetPerformance(current: Performance, foundPos: Int, performanceList: MutableList<Performance>): Boolean {
        return current.priority >= performanceList[foundPos].priority
    }
}