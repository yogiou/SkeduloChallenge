package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.FindBestPerformanceListService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
internal class FindBestPerformanceListServiceImplTest {
    @Autowired
    private lateinit var findBestPerformanceListService: FindBestPerformanceListService

    @Test
    fun findNextHigherPriorityPerformance() {
        val performance3 = Performance("Soundgarden", 5, "1993-05-25T02:00:00+10:00", "1993-05-25T02:15:00+10:00")
        val performance4 = Performance("Pearl Jam", 9,"1993-05-25T02:15:00+10:00", "1993-05-25T02:35:00+10:00")
        val performance5 = Performance("Soundgarden", 5,"1993-05-25T02:35:00+10:00", "1993-05-25T02:50:00+10:00")

        val queue = PriorityQueue<Performance>()
        queue.add(performance3)
        queue.add(performance4)
        queue.add(performance5)

        val performance = Performance("a", 1, "1993-05-25T02:00:00+10:00", "1993-05-25T01:00:00+10:00")
        Assertions.assertEquals(null, findBestPerformanceListService.findNextHigherPriorityPerformance(queue, performance))

        val performance1 = Performance("a", 1, "1993-05-25T02:00:00+10:00", "1993-05-25T03:00:00+10:00")
        Assertions.assertEquals(performance3, findBestPerformanceListService.findNextHigherPriorityPerformance(queue, performance1))

        val performance2 = Performance("a", 100, "1993-05-25T02:00:00+10:00", "1993-05-25T03:00:00+10:00")
        Assertions.assertEquals(null, findBestPerformanceListService.findNextHigherPriorityPerformance(queue, performance2))
    }

    @Test
    fun findBestPerformanceList() {
        val performance1 = Performance("Soundgarden", 5, "1993-05-25T02:00:00Z", "1993-05-25T02:50:00Z")
        val performance2 = Performance("Pearl Jam", 9,"1993-05-25T02:15:00Z", "1993-05-25T02:35:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance3 = Performance("Soundgarden", 5, "1993-05-25T02:00:00Z", "1993-05-25T02:15:00Z")
        val performance4 = Performance("Pearl Jam", 9,"1993-05-25T02:15:00Z", "1993-05-25T02:35:00Z")
        val performance5 = Performance("Soundgarden", 5,"1993-05-25T02:35:00Z", "1993-05-25T02:50:00Z")

        expected.add(performance3)
        expected.add(performance4)
        expected.add(performance5)

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
        val performance1 = Performance("Soundgarden", 5, "1993-05-25T02:00:00+10:00", "1993-05-25T02:50:00+10:00")
        val performance2 = Performance("Pearl Jam", 9, "1993-05-25T02:15:00+10:00", "1993-05-25T02:35:00+10:00")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance3 = Performance("Soundgarden", 5, "1993-05-25T02:00:00+10:00", "1993-05-25T02:15:00+10:00")
        val performance4 = Performance("Pearl Jam", 9,"1993-05-25T02:15:00+10:00", "1993-05-25T02:35:00+10:00")
        val performance5 = Performance("Soundgarden", 5,"1993-05-25T02:35:00+10:00", "1993-05-25T02:50:00+10:00")

        expected.add(performance3)
        expected.add(performance4)
        expected.add(performance5)

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
        val performance1 = Performance("Soundgarden", 5, "2018-05-25T00:00:00Z", "2018-05-25T01:50:00Z")
        val performance2 = Performance("Nirvana", 1, "2018-05-25T00:30:00Z", "2018-05-25T02:00:00Z")
        val performance3 = Performance("Pearl Jam", 10,"2018-05-25T00:15:00Z", "2018-05-25T01:35:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Soundgarden", 5, "2018-05-25T00:00:00Z", "2018-05-25T00:15:00Z")
        val performance5 = Performance("Pearl Jam", 10,"2018-05-25T00:15:00Z", "2018-05-25T01:35:00Z")
        val performance6 = Performance("Soundgarden", 5,"2018-05-25T01:35:00Z", "2018-05-25T01:50:00Z")
        val performance7 = Performance("Nirvana", 1, "2018-05-25T01:50:00Z", "2018-05-25T02:00:00Z")

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)

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
        val performance1 = Performance("2. Pearl Jam", 10, "2018-05-25T00:05:00Z", "2018-05-25T00:25:00Z")
        val performance2 = Performance("5. Nirvana finishes one minute after Soundgarden", 1, "2018-05-25T00:10:00Z", "2018-05-25T00:46:00Z")
        val performance3 = Performance("1. 3. Red Hot Chili Peppers", 9,"2018-05-25T00:00:00Z", "2018-05-25T00:35:00Z")
        val performance4 = Performance("6. Rage Against The Machine", 4,"2018-05-25T01:10:00Z", "2018-05-25T01:30:00Z")
        val performance5 = Performance("4. Soundgarden", 5,"2018-05-25T00:00:00Z", "2018-05-25T00:45:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performance5)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance6 = Performance("1. 3. Red Hot Chili Peppers", 9, "2018-05-25T00:00:00Z", "2018-05-25T00:05:00Z")
        val performance7 = Performance("2. Pearl Jam", 10,"2018-05-25T00:05:00Z", "2018-05-25T00:25:00Z")
        val performance8 = Performance("1. 3. Red Hot Chili Peppers", 9,"2018-05-25T00:25:00Z", "2018-05-25T00:35:00Z")
        val performance9 = Performance("4. Soundgarden", 5, "2018-05-25T00:35:00Z", "2018-05-25T00:45:00Z")
        val performance10 = Performance("5. Nirvana finishes one minute after Soundgarden", 1, "2018-05-25T00:45:00Z", "2018-05-25T00:46:00Z")
        val performance11 = Performance("6. Rage Against The Machine", 4, "2018-05-25T01:10:00Z", "2018-05-25T01:30:00Z")

        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performance9)
        expected.add(performance10)
        expected.add(performance11)

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
        val performance1 = Performance("Pearl Jam", 10, "2018-05-25T00:01:00Z", "2018-05-25T00:28:00Z")
        val performance2 = Performance("Red Hot Chili Peppers", 9, "2018-05-25T00:00:00Z", "2018-05-25T00:35:00Z")
        val performance3 = Performance("Soundgarden", 5,"2018-05-25T00:00:00Z", "2018-05-25T00:45:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Red Hot Chili Peppers", 9, "2018-05-25T00:00:00Z", "2018-05-25T00:01:00Z")
        val performance5 = Performance("Pearl Jam", 10,"2018-05-25T00:01:00Z", "2018-05-25T00:28:00Z")
        val performance6 = Performance("Red Hot Chili Peppers", 9,"2018-05-25T00:28:00Z", "2018-05-25T00:35:00Z")
        val performance7 = Performance("Soundgarden", 5, "2018-05-25T00:35:00Z", "2018-05-25T00:45:00Z")

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)

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
        val performance1 = Performance("Pearl Jam", 10, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance2 = Performance("Red Hot Chili Peppers", 9, "2018-05-25T00:00:00Z", "2018-05-25T00:35:00Z")
        val performance3 = Performance("Soundgarden", 5,"2018-05-25T00:00:00Z", "2018-05-25T00:45:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Red Hot Chili Peppers", 9, "2018-05-25T00:00:00Z", "2018-05-25T00:01:15Z")
        val performance5 = Performance("Pearl Jam", 10,"2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance6 = Performance("Red Hot Chili Peppers", 9,"2018-05-25T00:28:30Z", "2018-05-25T00:35:00Z")
        val performance7 = Performance("Soundgarden", 5, "2018-05-25T00:35:00Z", "2018-05-25T00:45:00Z")

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance2 = Performance("Red Hot Chili Peppers", 5, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance3 = Performance("Soundgarden", 9,"2018-05-25T00:03:00Z", "2018-05-25T00:10:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance4 = Performance("Red Hot Chili Peppers", 5, "2018-05-25T00:01:15Z", "2018-05-25T00:03:00Z")
        val performance5 = Performance("Soundgarden", 9,"2018-05-25T00:03:00Z", "2018-05-25T00:10:00Z")
        val performance6 = Performance("Red Hot Chili Peppers", 5,"2018-05-25T00:10:00Z", "2018-05-25T00:28:30Z")

        expected.add(performance4)
        expected.add(performance5)
        expected.add(performance6)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance2 = Performance("Pearl Jam1", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance3 = Performance("Pearl Jam2", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance4 = Performance("Pearl Jam3", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:28:30Z")
        val performance5 = Performance("Soundgarden", 9,"2018-05-25T00:01:15Z", "2018-05-25T00:10:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performance5)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance6 = Performance("Soundgarden", 9,"2018-05-25T00:01:15Z", "2018-05-25T00:10:00Z")
        val performance7 = Performance("Pearl Jam", 2, "2018-05-25T00:10:00Z", "2018-05-25T00:28:30Z")

        expected.add(performance6)
        expected.add(performance7)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:04:30Z")
        val performance3 = Performance("Pearl Jam2", 4, "2018-05-25T00:03:00Z", "2018-05-25T00:05:30Z")
        val performance4 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceM = Performance("Pearl Jam4", 1, "2018-05-25T00:50:00Z", "2018-05-25T00:59:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:02:00Z")
        val performance6 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:03:00Z")
        val performance7 = Performance("Pearl Jam2", 4, "2018-05-25T00:03:00Z", "2018-05-25T00:04:00Z")
        val performance8 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceN = Performance("Pearl Jam4", 1, "2018-05-25T00:50:00Z", "2018-05-25T00:59:30Z")

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performanceN)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:04:30Z")
        val performance4 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceM = Performance("Pearl Jam4", 1, "2018-05-25T00:01:20Z", "2018-05-25T00:59:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:02:00Z")
        val performance6 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:04:00Z")
        val performance8 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceN = Performance("Pearl Jam4", 1, "2018-05-25T00:06:30Z", "2018-05-25T00:59:30Z")

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance8)
        expected.add(performanceN)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:04:30Z")
        val performance3 = Performance("Pearl Jam2", 4, "2018-05-25T00:03:00Z", "2018-05-25T00:05:30Z")
        val performance4 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceM = Performance("Pearl Jam4", 1, "2018-05-25T00:01:20Z", "2018-05-25T00:59:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance3)
        list.add(performance4)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:02:00Z")
        val performance6 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:03:00Z")
        val performance7 = Performance("Pearl Jam2", 4, "2018-05-25T00:03:00Z", "2018-05-25T00:04:00Z")
        val performance8 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceN = Performance("Pearl Jam4", 1, "2018-05-25T00:06:30Z", "2018-05-25T00:59:30Z")

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performanceN)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:03:30Z")
        val performance4 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceM = Performance("Pearl Jam4", 1, "2018-05-25T00:01:20Z", "2018-05-25T00:59:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:02:00Z")
        val performance6 = Performance("Pearl Jam1", 3, "2018-05-25T00:02:00Z", "2018-05-25T00:03:30Z")
        val performanceK = Performance("Pearl Jam4", 1, "2018-05-25T00:03:30Z", "2018-05-25T00:04:00Z")
        val performance8 = Performance("Pearl Jam3", 5, "2018-05-25T00:04:00Z", "2018-05-25T00:06:30Z")
        val performanceN = Performance("Pearl Jam4", 1, "2018-05-25T00:06:30Z", "2018-05-25T00:59:30Z")

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performanceK)
        expected.add(performance8)
        expected.add(performanceN)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 3, "2018-05-25T00:09:00Z", "2018-05-25T00:10:30Z")
        val performance4 = Performance("Pearl Jam3", 5, "2018-05-25T00:20:00Z", "2018-05-25T00:26:30Z")
        val performanceM = Performance("Pearl Jam4", 1, "2018-05-25T00:38:20Z", "2018-05-25T00:59:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance6 = Performance("Pearl Jam1", 3, "2018-05-25T00:09:00Z", "2018-05-25T00:10:30Z")
        val performance7 = Performance("Pearl Jam3", 5, "2018-05-25T00:20:00Z", "2018-05-25T00:26:30Z")
        val performanceN = Performance("Pearl Jam4", 1, "2018-05-25T00:38:20Z", "2018-05-25T00:59:30Z")

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performanceN)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 1, "2018-05-25T00:02:00Z", "2018-05-25T00:10:30Z")
        val performance4 = Performance("Pearl Jam3", 3, "2018-05-25T00:04:00Z", "2018-05-25T00:05:30Z")
        val performance3 = Performance("Pearl Jam4", 5, "2018-05-25T00:07:20Z", "2018-05-25T00:09:30Z")
        val performanceM = Performance("Pearl Jam5", 10, "2018-05-25T00:27:20Z", "2018-05-25T00:49:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance6 = Performance("Pearl Jam1", 1, "2018-05-25T00:03:30Z", "2018-05-25T00:04:00Z")
        val performance7 = Performance("Pearl Jam3", 3, "2018-05-25T00:04:00Z", "2018-05-25T00:05:30Z")
        val performance8 = Performance("Pearl Jam1", 1, "2018-05-25T00:05:30Z", "2018-05-25T00:07:20Z")
        val performance9 = Performance("Pearl Jam4", 5, "2018-05-25T00:07:20Z", "2018-05-25T00:09:30Z")
        val performance10 = Performance("Pearl Jam1", 1, "2018-05-25T00:09:30Z", "2018-05-25T00:10:30Z")
        val performance11 = Performance("Pearl Jam5", 10, "2018-05-25T00:27:20Z", "2018-05-25T00:49:30Z")

        expected.add(performance5)
        expected.add(performance6)
        expected.add(performance7)
        expected.add(performance8)
        expected.add(performance9)
        expected.add(performance10)
        expected.add(performance11)

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
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 1, "2018-05-25T00:02:00Z", "2018-05-25T00:10:30Z")
        val performance4 = Performance("Pearl Jam3", 3, "2018-05-25T00:04:00Z", "2018-05-25T00:05:30Z")
        val performance3 = Performance("Pearl Jam4", 5, "2018-05-25T00:07:20Z", "2018-05-25T00:09:30Z")
        val performanceM = Performance("Pearl Jam5", 10, "2018-05-25T00:00:20Z", "2018-05-25T00:49:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam5", 10, "2018-05-25T00:00:20Z", "2018-05-25T00:49:30Z")

        expected.add(performance5)

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }

    @Test
    fun findBestPerformanceLis10() {
        val performance1 = Performance("Pearl Jam", 2, "2018-05-25T00:01:15Z", "2018-05-25T00:03:30Z")
        val performance2 = Performance("Pearl Jam1", 1, "2018-05-25T00:02:00Z", "2018-05-25T00:10:30Z")
        val performance4 = Performance("Pearl Jam3", 3, "2018-05-25T00:04:00Z", "2018-05-25T00:05:30Z")
        val performance3 = Performance("Pearl Jam4", 5, "2018-05-25T00:07:20Z", "2018-05-25T00:09:30Z")
        val performanceM = Performance("Pearl Jam5", 10, "2018-05-25T00:00:20Z", "2018-05-25T00:05:30Z")
        val performanceM1 = Performance("Pearl Jam6", 11, "2018-05-25T00:04:20Z", "2018-05-25T00:15:30Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)
        list.add(performance4)
        list.add(performance3)
        list.add(performanceM)
        list.add(performanceM1)

        val result = findBestPerformanceListService.findBestPerformanceList(list)

        val expected = mutableListOf<Performance>()

        val performance5 = Performance("Pearl Jam5", 10, "2018-05-25T00:00:20Z", "2018-05-25T00:04:20Z")
        val performance6 = Performance("Pearl Jam6", 11, "2018-05-25T00:04:20Z", "2018-05-25T00:15:30Z")

        expected.add(performance5)
        expected.add(performance6)

        Assertions.assertEquals(expected.size, result.size)

        for (i in 0 until result.size) {
            Assertions.assertEquals(expected[i].band, result[i].band)
            Assertions.assertEquals(expected[i].priority, result[i].priority)
            Assertions.assertEquals(expected[i].start, result[i].start)
            Assertions.assertEquals(expected[i].finish, result[i].finish)
        }
    }
}