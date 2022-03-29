package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.OutputPerformanceJsonFileService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OutputPerformanceJsonFileServiceImplTest {
    @Autowired
    private lateinit var outputPerformanceJsonFileService: OutputPerformanceJsonFileService

    @Test
    fun generateJsonString() {
        val performance1 = Performance("Soundgarden", 5, "1993-05-25T02:00:00Z", "1993-05-25T02:50:00Z")
        val performance2 = Performance("Pearl Jam", 9,"1993-05-25T02:15:00Z", "1993-05-25T02:35:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        val expected = "[\n" +
                "{\n" +
                "\"band\":\"Soundgarden\",\n" +
                "\"start\":\"1993-05-25T02:00:00Z\",\n" +
                "\"finish\":\"1993-05-25T02:50:00Z\"" +
                "},\n" +
                "{\n" +
                "\"band\":\"Pearl Jam\",\n" +
                "\"start\":\"1993-05-25T02:15:00Z\",\n" +
                "\"finish\":\"1993-05-25T02:35:00Z\"\n" +
                "}\n" +
                "]"
        val reulst = outputPerformanceJsonFileService.generateJsonString(list)

        assertEquals(expected.replace("\n", ""), reulst)
    }
}