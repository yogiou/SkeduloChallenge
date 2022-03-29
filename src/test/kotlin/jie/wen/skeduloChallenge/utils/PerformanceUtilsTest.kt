package jie.wen.skeduloChallenge.utils

import jie.wen.skeduloChallenge.data.Performance
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PerformanceUtilsTest {

    @Test
    fun isPerformanceValid() {
        val performance = Performance("test", 1, "2018-05-25T00:30:00Z", "2018-05-25T02:00:00Z")
        val emptyList = mutableListOf<Performance>()
        assertTrue(PerformanceUtils.isPerformanceValid(emptyList, performance))

        val list = mutableListOf<Performance>()
        list.add(Performance("test1", 2, "2018-05-25T00:30:00Z", "2018-05-25T04:00:00Z"))
        assertFalse(PerformanceUtils.isPerformanceValid(list, performance))

        val list2 = mutableListOf<Performance>()
        list2.add(Performance("test1", 2, "2018-05-25T00:30:00Z", "2018-05-25T01:00:00Z"))
        assertTrue(PerformanceUtils.isPerformanceValid(list2, performance))
    }
}