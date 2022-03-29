package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.ReadInputJsonFileService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ReadInputJsonFileServiceImplTest {
    @Autowired
    private lateinit var readInputJsonFileService: ReadInputJsonFileService

    @Test
    fun parseJson() {
        val input = "[\n" +
                " {\n" +
                "   \"band\" : \"Soundgarden\",\n" +
                "   \"start\" : \"1993-05-25T02:00:00Z\",\n" +
                "   \"finish\" : \"1993-05-25T02:50:00Z\",\n" +
                "   \"priority\" : 5\n" +
                " },\n" +
                " {\n" +
                "   \"band\" : \"Pearl Jam\",\n" +
                "   \"start\" : \"1993-05-25T02:15:00Z\",\n" +
                "   \"finish\" : \"1993-05-25T02:35:00Z\",\n" +
                "   \"priority\" : 9\n" +
                " }\n" +
                "]"

        val performance1 = Performance("Soundgarden", 5, "1993-05-25T02:00:00Z", "1993-05-25T02:50:00Z")
        val performance2 = Performance("Pearl Jam", 9,"1993-05-25T02:15:00Z", "1993-05-25T02:35:00Z")

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        val jsonObject = readInputJsonFileService.parseJson(input)

        assertEquals(list.size, jsonObject.size)
        for (i in 0 until list.size) {
            assertEquals(list[i].band, jsonObject[i].band)
            assertEquals(list[i].priority, jsonObject[i].priority)
            assertEquals(list[i].start, jsonObject[i].start)
            assertEquals(list[i].finish, jsonObject[i].finish)
        }
    }
}