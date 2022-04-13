package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Constants.SHOW_TEST_RESULT
import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.FindBestPerformanceListService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@SpringBootTest
internal class FindBestPerformanceListServiceImplTest {
    @Autowired
    private lateinit var findBestPerformanceListService: FindBestPerformanceListService


    @Test
    fun findBestPerformanceList() {
        val performance1 = Performance("Soundgarden", 5, Date.from(Instant.parse("1993-05-25T02:00:00Z")), Date.from(Instant.parse("1993-05-25T02:50:00Z")))
        val performance2 = Performance("Pearl Jam", 9,Date.from(Instant.parse("1993-05-25T02:15:00Z")), Date.from(Instant.parse("1993-05-25T02:35:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance3 = Performance("Soundgarden", 5, Date.from(Instant.parse("1993-05-25T02:00:00Z")), Date.from(Instant.parse("1993-05-25T02:15:00Z")))
        val performance4 = Performance("Pearl Jam", 9,Date.from(Instant.parse("1993-05-25T02:15:00Z")), Date.from(Instant.parse("1993-05-25T02:35:00Z")))
        val performance5 = Performance("Soundgarden", 5,Date.from(Instant.parse("1993-05-25T02:35:00Z")), Date.from(Instant.parse("1993-05-25T02:50:00Z")))

        expected.add(performance3)
        expected.add(performance4)
        expected.add(performance5)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceListWithOffset() {
        val performance1 = Performance("Soundgarden", 5, Date.from(Instant.parse("1993-05-25T02:00:00+10:00")), Date.from(Instant.parse("1993-05-25T02:50:00+10:00")))
        val performance2 = Performance("Pearl Jam", 9, Date.from(Instant.parse("1993-05-25T02:15:00+10:00")), Date.from(Instant.parse("1993-05-25T02:35:00+10:00")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance3 = Performance("Soundgarden", 5, Date.from(Instant.parse("1993-05-25T02:00:00+10:00")), Date.from(Instant.parse("1993-05-25T02:15:00+10:00")))
        val performance4 = Performance("Pearl Jam", 9,Date.from(Instant.parse("1993-05-25T02:15:00+10:00")), Date.from(Instant.parse("1993-05-25T02:35:00+10:00")))
        val performance5 = Performance("Soundgarden", 5,Date.from(Instant.parse("1993-05-25T02:35:00+10:00")), Date.from(Instant.parse("1993-05-25T02:50:00+10:00")))

        expected.add(performance3)
        expected.add(performance4)
        expected.add(performance5)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceListOverlap() {
        val performance1 = Performance("Soundgarden", 5, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T01:50:00Z")))
        val performance2 = Performance("Nirvana", 1, Date.from(Instant.parse("2018-05-25T00:30:00Z")), Date.from(Instant.parse("2018-05-25T02:00:00Z")))
        val performance3 = Performance("Pearl Jam", 10,Date.from(Instant.parse("2018-05-25T00:15:00Z")), Date.from(Instant.parse("2018-05-25T01:35:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Soundgarden", 5, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:15:00Z")))
        val performance5 = Performance("Pearl Jam", 10,Date.from(Instant.parse("2018-05-25T00:15:00Z")), Date.from(Instant.parse("2018-05-25T01:35:00Z")))
        val performance6 = Performance("Soundgarden", 5,Date.from(Instant.parse("2018-05-25T01:35:00Z")), Date.from(Instant.parse("2018-05-25T01:50:00Z")))
        val performance7 = Performance("Nirvana", 1, Date.from(Instant.parse("2018-05-25T01:50:00Z")), Date.from(Instant.parse("2018-05-25T02:00:00Z")))

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceListTimePriority() {
        val performance1 = Performance("2. Pearl Jam", 10, Date.from(Instant.parse("2018-05-25T00:05:00Z")), Date.from(Instant.parse("2018-05-25T00:25:00Z")))
        val performance2 = Performance("5. Nirvana finishes one minute after Soundgarden", 1, Date.from(Instant.parse("2018-05-25T00:10:00Z")), Date.from(Instant.parse("2018-05-25T00:46:00Z")))
        val performance3 = Performance("1. 3. Red Hot Chili Peppers", 9,Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:35:00Z")))
        val performance4 = Performance("6. Rage Against The Machine", 4,Date.from(Instant.parse("2018-05-25T01:10:00Z")), Date.from(Instant.parse("2018-05-25T01:30:00Z")))
        val performance5 = Performance("4. Soundgarden", 5,Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:45:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performance5)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance6 = Performance("1. 3. Red Hot Chili Peppers", 9, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:05:00Z")))
        val performance7 = Performance("2. Pearl Jam", 10,Date.from(Instant.parse("2018-05-25T00:05:00Z")), Date.from(Instant.parse("2018-05-25T00:25:00Z")))
        val performance8 = Performance("1. 3. Red Hot Chili Peppers", 9,Date.from(Instant.parse("2018-05-25T00:25:00Z")), Date.from(Instant.parse("2018-05-25T00:35:00Z")))
        val performance9 = Performance("4. Soundgarden", 5, Date.from(Instant.parse("2018-05-25T00:35:00Z")), Date.from(Instant.parse("2018-05-25T00:45:00Z")))
        val performance10 = Performance("5. Nirvana finishes one minute after Soundgarden", 1, Date.from(Instant.parse("2018-05-25T00:45:00Z")), Date.from(Instant.parse("2018-05-25T00:46:00Z")))
        val performance11 = Performance("6. Rage Against The Machine", 4, Date.from(Instant.parse("2018-05-25T01:10:00Z")), Date.from(Instant.parse("2018-05-25T01:30:00Z")))

        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performance9)
        expected.add(performance10)
        expected.add(performance11)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceListMinutes() {
        val performance1 = Performance("Pearl Jam", 10, Date.from(Instant.parse("2018-05-25T00:01:00Z")), Date.from(Instant.parse("2018-05-25T00:28:00Z")))
        val performance2 = Performance("Red Hot Chili Peppers", 9, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:35:00Z")))
        val performance3 = Performance("Soundgarden", 5,Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:45:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Red Hot Chili Peppers", 9, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:01:00Z")))
        val performance5 = Performance("Pearl Jam", 10,Date.from(Instant.parse("2018-05-25T00:01:00Z")), Date.from(Instant.parse("2018-05-25T00:28:00Z")))
        val performance6 = Performance("Red Hot Chili Peppers", 9,Date.from(Instant.parse("2018-05-25T00:28:00Z")), Date.from(Instant.parse("2018-05-25T00:35:00Z")))
        val performance7 = Performance("Soundgarden", 5, Date.from(Instant.parse("2018-05-25T00:35:00Z")), Date.from(Instant.parse("2018-05-25T00:45:00Z")))

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLisSeconds() {
        val performance1 = Performance("Pearl Jam", 10, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance2 = Performance("Red Hot Chili Peppers", 9, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:35:00Z")))
        val performance3 = Performance("Soundgarden", 5,Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:45:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Red Hot Chili Peppers", 9, Date.from(Instant.parse("2018-05-25T00:00:00Z")), Date.from(Instant.parse("2018-05-25T00:01:15Z")))
        val performance5 = Performance("Pearl Jam", 10,Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance6 = Performance("Red Hot Chili Peppers", 9,Date.from(Instant.parse("2018-05-25T00:28:30Z")), Date.from(Instant.parse("2018-05-25T00:35:00Z")))
        val performance7 = Performance("Soundgarden", 5, Date.from(Instant.parse("2018-05-25T00:35:00Z")), Date.from(Instant.parse("2018-05-25T00:45:00Z")))

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis1() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance2 = Performance("Red Hot Chili Peppers", 5, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance3 = Performance("Soundgarden", 9,Date.from(Instant.parse("2018-05-25T00:03:00Z")), Date.from(Instant.parse("2018-05-25T00:10:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Red Hot Chili Peppers", 5, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:00Z")))
        val performance5 = Performance("Soundgarden", 9,Date.from(Instant.parse("2018-05-25T00:03:00Z")), Date.from(Instant.parse("2018-05-25T00:10:00Z")))
        val performance6 = Performance("Red Hot Chili Peppers", 5,Date.from(Instant.parse("2018-05-25T00:10:00Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis2() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance2 = Performance("Pearl Jam1", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance3 = Performance("Pearl Jam2", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance4 = Performance("Pearl Jam3", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))
        val performance5 = Performance("Soundgarden", 9,Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:10:00Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performance5)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance6 = Performance("Soundgarden", 9,Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:10:00Z")))
        val performance7 = Performance("Pearl Jam2", 2, Date.from(Instant.parse("2018-05-25T00:10:00Z")), Date.from(Instant.parse("2018-05-25T00:28:30Z")))

        expected.add(performance6)
        expected.add(performance7)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis3() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:04:30Z")))
        val performance3 = Performance("Pearl Jam2", 4, Date.from(Instant.parse("2018-05-25T00:03:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceM = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:50:00Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performanceM)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:02:00Z")))
        val performance6 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:03:00Z")))
        val performance7 = Performance("Pearl Jam2", 4, Date.from(Instant.parse("2018-05-25T00:03:00Z")), Date.from(Instant.parse("2018-05-25T00:04:00Z")))
        val performance8 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceN = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:50:00Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performanceN)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis4() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:04:30Z")))
        val performance4 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceM = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:01:20Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performanceM)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:02:00Z")))
        val performance6 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:04:00Z")))
        val performance8 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceN = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:06:30Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance8)
        expected.add(performanceN)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis5() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:04:30Z")))
        val performance3 = Performance("Pearl Jam2", 4, Date.from(Instant.parse("2018-05-25T00:03:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceM = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:01:20Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performanceM)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:02:00Z")))
        val performance6 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:03:00Z")))
        val performance7 = Performance("Pearl Jam2", 4, Date.from(Instant.parse("2018-05-25T00:03:00Z")), Date.from(Instant.parse("2018-05-25T00:04:00Z")))
        val performance8 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceN = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:06:30Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performanceN)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis6() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance4 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceM = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:01:20Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performanceM)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:02:00Z")))
        val performance6 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performanceK = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:03:30Z")), Date.from(Instant.parse("2018-05-25T00:04:00Z")))
        val performance8 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:06:30Z")))
        val performanceN = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:06:30Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performanceK)
        expected.add(performance8)
        expected.add(performanceN)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis7() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:09:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:20:00Z")), Date.from(Instant.parse("2018-05-25T00:26:30Z")))
        val performanceM = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:38:20Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performanceM)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance6 = Performance("Pearl Jam1", 3, Date.from(Instant.parse("2018-05-25T00:09:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance7 = Performance("Pearl Jam3", 5, Date.from(Instant.parse("2018-05-25T00:20:00Z")), Date.from(Instant.parse("2018-05-25T00:26:30Z")))
        val performanceN = Performance("Pearl Jam4", 1, Date.from(Instant.parse("2018-05-25T00:38:20Z")), Date.from(Instant.parse("2018-05-25T00:59:30Z")))

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performanceN)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis8() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance3 = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performanceM = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:27:20Z")), Date.from(Instant.parse("2018-05-25T00:49:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance6 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:03:30Z")), Date.from(Instant.parse("2018-05-25T00:04:00Z")))
        val performance7 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance8 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:05:30Z")), Date.from(Instant.parse("2018-05-25T00:07:20Z")))
        val performance9 = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performance10 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:09:30Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance11 = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:27:20Z")), Date.from(Instant.parse("2018-05-25T00:49:30Z")))

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performance9)
        expected.add(performance10)
        expected.add(performance11)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }


    @Test
    fun findBestPerformanceLis9() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance3 = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performanceM = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:49:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)

        val a = list.toMutableList()


        val result = findBestPerformanceListService.findBestPerformanceList(list)

        findBestPerformanceListService.show(result)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:49:30Z")))

        expected.add(performance5)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceList10() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance3 = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performanceM = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceM1 = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:04:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)
        list.add(performanceM1)

        val a = list.toMutableList()

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:04:20Z")))
        val performance6 = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:04:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))

        expected.add(performance5)
        expected.add(performance6)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(result)
        }

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    // compared to old linear search method, the processing time reduced from 41 seconds to 2.8 seconds
    @Test
    fun findBestPerformanceListPerformanceTest() {
        val size = 100000
        val list = mutableListOf<Performance>()
        for (i in 0 until size) {
            val ran = (Random().nextInt(100000) + 1).toLong()
            val s = Timestamp.from(Instant.now().plusSeconds(3600 * ran))
            val e = Timestamp.from(Instant.now().plusSeconds(3600 * ran).plusSeconds(3600 * (Random().nextInt(10) + 1).toLong()))
            val performance1 = Performance("Pearl Jam", 2, s, e)
            list.add(performance1)
        }

        val start = System.currentTimeMillis()
        findBestPerformanceListService.findBestPerformanceList(list)
        val end = System.currentTimeMillis()
        val time = (end - start).toFloat() / 1000F
        print("processing time: $time s")
        Assertions.assertTrue(time <= 5)
    }

    @Test
    fun addPerformanceTest() {
        val performance1 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2 = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s1 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s2 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s3 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance3 = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performanceM = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceM1 = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:04:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))

        var list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance4s1)
        list.add(performance4s2)
        list.add(performance4s3)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)
        list.add(performanceM1)
        list.sort()

        val a = list.toMutableList()

        val current = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:05:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))
        val c1 = Performance("Pearl Jam12", 12, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performanceME1 = Performance("Pearl Jam51", 10, Date.from(Instant.parse("2018-05-24T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceEnd = Performance("Pearl Jam41", 5, Date.from(Instant.parse("2018-05-29T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performance2E1 = Performance("Pearl Jam12", 19, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4sE = Performance("Pearl Jam31", 13, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4sEE = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))

        findBestPerformanceListService.addPerformance(list, current)
        findBestPerformanceListService.addPerformance(list, c1)
        findBestPerformanceListService.addPerformance(list, performanceME1)
        findBestPerformanceListService.addPerformance(list, performanceEnd)
        findBestPerformanceListService.addPerformance(list, performance2E1)
        findBestPerformanceListService.addPerformance(list, performance4sE)
        findBestPerformanceListService.addPerformance(list, performance4sEE)


        val expected = mutableListOf<Performance>()


        val performanceME = Performance("Pearl Jam5", 10, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance1E = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance2E = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4E = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceM1E = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:04:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))
        val performance3E = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))

        expected.add(performanceME1)
        expected.add(performanceME)
        expected.add(c1)
        expected.add(performance1E)
        expected.add(performance2E1)
        expected.add(performance2E)
        expected.add(performance4sE)
        expected.add(performance4E)
        expected.add(performance4sEE)
        expected.add(performance4s1)
        expected.add(performance4s2)
        expected.add(performance4s3)
        expected.add(performanceM1E)
        expected.add(current)
        expected.add(performance3E)
        expected.add(performanceEnd)

        if (SHOW_TEST_RESULT) {
            a.sort()
            findBestPerformanceListService.show(a)
            findBestPerformanceListService.show(expected)
            findBestPerformanceListService.show(list)
        }

        Assertions.assertEquals(expected.size, list.size)

        for (i in 0 until list.size) {
            Assertions.assertEquals(expected[i].band, list[i].band)
            Assertions.assertEquals(expected[i].priority, list[i].priority)
            Assertions.assertEquals(expected[i].start, list[i].start)
            Assertions.assertEquals(expected[i].finish, list[i].finish)
        }
    }

    @Test
    fun findNextPerformanceTest() {
        var list = mutableListOf<Performance>()

        val performanceME1 = Performance("Pearl Jam51", 10, Date.from(Instant.parse("2018-05-24T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceME = Performance("Pearl Jam5", 1, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))

        val c1 = Performance("Pearl Jam12", 12, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c2 = Performance("Pearl Jam 11", 11, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c3 = Performance("Pearl Jam 10", 10, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c4 = Performance("Pearl Jam 9", 9, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance1E = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))

        val performance2E = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4E = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceM1E = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:04:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))
        val performance3E = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val current = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:05:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))


        val performanceEnd = Performance("Pearl Jam41", 5, Date.from(Instant.parse("2018-05-29T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performance2E1 = Performance("Pearl Jam12", 19, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4sE = Performance("Pearl Jam31", 13, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4sEE = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s1 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s2 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s3 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))

//        list.add(performanceME1)
        list.add(performanceME)
        list.add(c1)
        list.add(c2)
        list.add(c3)
        list.add(c4)
        list.add(c5)
        list.add(performance1E)
        list.add(performance2E1)
        list.add(performance2E)
        list.add(performance4sE)
        list.add(performance4E)
        list.add(performance4sEE)
        list.add(performance4s1)
        list.add(performance4s2)
        list.add(performance4s3)
        list.add(performanceM1E)
        list.add(current)
        list.add(performance3E)
        list.add(performanceEnd)

        list.sort()

        val result = findBestPerformanceListService.findNextPerformance(list, performanceME1)

        result?.let {
            Assertions.assertTrue(it.band == c1.band && it.start == c1.start && it.finish == c1.finish && it.priority == c1.priority)
        }
    }

    @Test
    fun findNextPerformanceTest1() {
        var list = mutableListOf<Performance>()

        val performanceME1 = Performance("Pearl Jam51", 2, Date.from(Instant.parse("2018-05-24T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))
        val performanceME = Performance("Pearl Jam5", 1, Date.from(Instant.parse("2018-05-25T00:00:20Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))

        val c1 = Performance("Pearl Jam12", 10, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c2 = Performance("Pearl Jam 11", 11, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c3 = Performance("Pearl Jam 10", 10, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c4 = Performance("Pearl Jam 9", 9, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val c5 = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))
        val performance1E = Performance("Pearl Jam", 2, Date.from(Instant.parse("2018-05-25T00:01:15Z")), Date.from(Instant.parse("2018-05-25T00:03:30Z")))

        val performance2E = Performance("Pearl Jam1", 1, Date.from(Instant.parse("2018-05-25T00:02:00Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4E = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performanceM1E = Performance("Pearl Jam6", 11, Date.from(Instant.parse("2018-05-25T00:04:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))
        val performance3E = Performance("Pearl Jam4", 5, Date.from(Instant.parse("2018-05-25T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val current = Performance("Pearl Jam6", 15, Date.from(Instant.parse("2018-05-25T00:01:20Z")), Date.from(Instant.parse("2018-05-25T00:15:30Z")))


        val performanceEnd = Performance("Pearl Jam41", 5, Date.from(Instant.parse("2018-05-29T00:07:20Z")), Date.from(Instant.parse("2018-05-25T00:09:30Z")))
        val performance2E1 = Performance("Pearl Jam12", 19, Date.from(Instant.parse("2018-05-25T00:00:40Z")), Date.from(Instant.parse("2018-05-25T00:10:30Z")))
        val performance4sE = Performance("Pearl Jam31", 13, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4sEE = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s1 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s2 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))
        val performance4s3 = Performance("Pearl Jam3", 3, Date.from(Instant.parse("2018-05-25T00:04:00Z")), Date.from(Instant.parse("2018-05-25T00:05:30Z")))

//        list.add(performanceME1)
        list.add(performanceME)
        list.add(c1)
        list.add(c2)
        list.add(c3)
        list.add(c4)
        list.add(c5)
        list.add(performance1E)
        list.add(performance2E1)
        list.add(performance2E)
        list.add(performance4sE)
        list.add(performance4E)
        list.add(performance4sEE)
        list.add(performance4s1)
        list.add(performance4s2)
        list.add(performance4s3)
        list.add(performanceM1E)
        list.add(current)
        list.add(performance3E)
        list.add(performanceEnd)

        list.sort()

        findBestPerformanceListService.show(list)

        val result = findBestPerformanceListService.findNextPerformance(list, performanceME1)

        result?.let {
            val a = mutableListOf<Performance>()
            a.add(it)
            findBestPerformanceListService.show(a)
            Assertions.assertTrue(it.band == performance2E1.band && it.start == performance2E1.start && it.finish == performance2E1.finish && it.priority == performance2E1.priority)
        }
    }
}