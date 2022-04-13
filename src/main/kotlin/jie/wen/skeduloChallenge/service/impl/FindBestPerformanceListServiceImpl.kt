package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.FindBestPerformanceListService
import jie.wen.skeduloChallenge.utils.PerformanceUtils
import jie.wen.skeduloChallenge.utils.TimeUtils
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
            if (result.isNotEmpty() && result.last().finish < current.finish && current.start < result.last().finish) {
                val performance = Performance(current.band, current.priority, result.last().finish, current.finish)
                addPerformance(performanceList, performance)
            } else {
                if (performanceList.isNotEmpty()) {
                    // check if the performance is still ongoing
                    if (PerformanceUtils.isPerformanceValid(result, current)) {
                        // get the next most recent performance with higher priority which the start time is in the period of the current performance
                        val next: Performance? = findNextPerformance(performanceList, current)

                        next?.apply {
                            if (current.finish > next.finish) {
                                // if incoming higher priority performance is overlapped with current but will finish after the incoming one, split and add it to priority queue
                                val performanceObject = Performance(current.band, current.priority, this.finish, current.finish)
                                addPerformance(performanceList, performanceObject)
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
                    // no more performance in the list, add it in the result directly
                    if (result.isEmpty() || result.last().finish < current.finish) {
                        result.add(current)
                    }
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

            if (current.start == performanceList[mid].start) {
                if (foundEnd == -1) {
                    while (s <= e) {
                        subMid = (e - s) / 2 + s
                        if (subMid == performanceList.size - 1 || current.start < performanceList[subMid + 1].start) {
                            foundEnd = subMid

                            subMid = -1
                            s = -1
                            e = -1

                            break
                        } else {
                            s = subMid + 1
                        }
                    }
                } else if (foundStart == -1) {
                    while (s <= e) {
                        subMid = (e - s) / 2 + s
                        if (subMid == 0 || current.start > performanceList[subMid - 1].start) {
                            foundStart = subMid
                            break
                        } else {
                            e = subMid - 1
                        }
                    }
                } else {
                    break
                }
            } else if (mid > 0 && current.start < performanceList[mid].start && current.start > performanceList[mid - 1].start) {
                performanceList.add(mid, current)
                return
            } else if (mid == 0) {
                if (current.start < performanceList[mid].start) {
                    performanceList.add(mid, current)
                    return
                } else {
                    if (start == end) {
                        performanceList.add(mid + 1, current)
                        return
                    } else {
                        start = mid + 1
                    }
                }
            } else if (mid == performanceList.size - 1) {
                if (current.start < performanceList[mid].start) {
                    if (start == end) {
                        performanceList.add(mid , current)
                        return
                    } else {
                        end = mid - 1
                    }
                } else {
                    performanceList.add(mid + 1, current)
                    return
                }
            } else if (current.start > performanceList[mid].start) {
                start = mid + 1
            } else {
                end = mid - 1
            }
        }

        start = foundStart
        end = foundEnd
        while (start in 0..end) {
            mid = (end - start) / 2 + start

            if (mid == foundStart) {
                if (current.priority >= performanceList[mid].priority) {
                    performanceList.add(mid, current)
                } else {
                    performanceList.add(mid + 1, current)
                }

                return
            } else if (mid == foundEnd) {
                if (current.priority >= performanceList[mid].priority) {
                    performanceList.add(mid, current)
                } else {
                    performanceList.add(mid + 1, current)
                }

                return
            } else if (performanceList[mid - 1].priority >= current.priority && performanceList[mid].priority <= current.priority) {
                performanceList.add(mid, current)
                return
            } else if (performanceList[mid].priority < current.priority) {
                end = mid - 1
            } else {
                start = mid + 1
            }
        }
    }

    // find the next performance with higher priority within current performance period (search by binary search)
    override fun findNextPerformance(list: MutableList<Performance>, current: Performance): Performance? {
        var performanceList = list.toMutableList()
        var result: Performance? = null
        var start = 0
        var end = performanceList.size - 1
        var mid: Int

        while (start <= end) {
            mid = (end - start) / 2 + start

            if (mid == 0) {
                if (performanceList[mid].start >= current.start && performanceList[mid].start <= current.finish && current.priority < performanceList[mid].priority) {
                    result = performanceList[mid]
                    break
                } else {
                    performanceList = performanceList.subList(1, performanceList.size)
                    start = 0
                    end = performanceList.size - 1
                }
            } else if (mid == performanceList.size - 1) {
                if (performanceList[mid].start >= current.start && performanceList[mid].start <= current.finish && current.priority < performanceList[mid].priority) {
                    result = performanceList[mid]
                }

                break
            } else if (current.priority < performanceList[mid].priority
                && performanceList[mid - 1].start == performanceList[0].start
                && performanceList[mid].start > performanceList[mid - 1].start
                && performanceList[mid].start <= performanceList[mid + 1].start
                && performanceList[0].priority <= current.priority
            ) {
                if (performanceList[mid].start >= current.start && performanceList[mid].start <= current.finish) {
                    result = performanceList[mid]
                }

                break
            } else if (performanceList[mid - 1].start == performanceList[mid].start) {
                start = mid + 1
            } else if (current.priority >= performanceList[mid].priority) {
                performanceList = performanceList.subList(mid, performanceList.size)
                start = 0
                end = performanceList.size - 1
            } else {
                end = mid - 1
            }
        }

        return result
    }

    // for debugging
    override fun show(performanceList: MutableList<Performance>) {
        for (performance in performanceList) {
            println("${performance.band} ${TimeUtils.printTime(performance.start)} ${TimeUtils.printTime(performance.finish)} ${performance.priority}")
        }

        println()
    }
}