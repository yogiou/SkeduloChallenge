package jie.wen.skeduloChallenge.utils

import jie.wen.skeduloChallenge.data.Performance
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant

internal class PerformanceUtilsTest {

    @Test
    fun isPerformanceValid() {
        val performance = Performance("test", 1, Timestamp(Date.from(Instant.parse("2018-05-25T00:30:00Z")).time), Timestamp(Date.from(Instant.parse("2018-05-25T02:00:00Z")).time))
        val emptyList = mutableListOf<Performance>()
        assertTrue(PerformanceUtils.isPerformanceValid(emptyList, performance))

        val list = mutableListOf<Performance>()
        list.add(Performance("test1", 2, Timestamp(Date.from(Instant.parse("2018-05-25T00:30:00Z")).time), Timestamp(Date.from(Instant.parse("2018-05-25T04:00:00Z")).time)))
        assertFalse(PerformanceUtils.isPerformanceValid(list, performance))

        val list2 = mutableListOf<Performance>()
        list2.add(Performance("test1", 2, Timestamp(Date.from(Instant.parse("2018-05-25T00:30:00Z")).time), Timestamp(Date.from(Instant.parse("2018-05-25T01:00:00Z")).time)))
        assertTrue(PerformanceUtils.isPerformanceValid(list2, performance))
    }
}