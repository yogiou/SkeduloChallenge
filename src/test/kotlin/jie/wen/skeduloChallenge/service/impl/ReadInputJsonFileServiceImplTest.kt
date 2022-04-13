package jie.wen.skeduloChallenge.service.impl

import jie.wen.skeduloChallenge.data.Constants.SHOW_TEST_RESULT
import jie.wen.skeduloChallenge.data.Performance
import jie.wen.skeduloChallenge.service.ReadInputJsonFileService
import jie.wen.skeduloChallenge.utils.TimeUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Date
import java.time.Instant

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

        val performance1 = Performance("Soundgarden", 5, Date.from(Instant.parse("1993-05-25T02:00:00Z")), Date.from(Instant.parse("1993-05-25T02:50:00Z")))
        val performance2 = Performance("Pearl Jam", 9,Date.from(Instant.parse("1993-05-25T02:15:00Z")), Date.from(Instant.parse("1993-05-25T02:35:00Z")))

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

    @Test
    fun parseJson2() {
        val input = "[\n" +
                " {\n" +
                "   \"band\" : \"Soundgarden\",\n" +
                "   \"start\" : \"1993-05-25T02:00:00+10:00\",\n" +
                "   \"finish\" : \"1993-05-25T02:50:00+10:00\",\n" +
                "   \"priority\" : 5\n" +
                " },\n" +
                " {\n" +
                "   \"band\" : \"Pearl Jam\",\n" +
                "   \"start\" : \"1993-05-25T02:15:00+10:00\",\n" +
                "   \"finish\" : \"1993-05-25T02:35:00+10:00\",\n" +
                "   \"priority\" : 9\n" +
                " }\n" +
                "]"

        val performance1 = Performance("Soundgarden", 5, Date.from(Instant.parse("1993-05-25T02:00:00+10:00")), Date.from(Instant.parse("1993-05-25T02:50:00+10:00")))
        val performance2 = Performance("Pearl Jam", 9,Date.from(Instant.parse("1993-05-25T02:15:00+10:00")), Date.from(Instant.parse("1993-05-25T02:35:00+10:00")))

        val list = mutableListOf<Performance>()
        list.add(performance1)
        list.add(performance2)

        TimeUtils.getTimeZoneFromJson(input)
        val jsonObject = readInputJsonFileService.parseJson(input)

        assertEquals(list.size, jsonObject.size)
        for (i in 0 until list.size) {
            if (SHOW_TEST_RESULT) {
                println("expected: ")
                println("Start: ${TimeUtils.printTime(list[i].start)}, end: ${TimeUtils.printTime(list[i].finish)}")

                println("actual: ")
                println("Start: ${TimeUtils.printTime(jsonObject[i].start)}, end: ${TimeUtils.printTime(jsonObject[i].finish)}")
            }

            assertEquals(list[i].band, jsonObject[i].band)
            assertEquals(list[i].priority, jsonObject[i].priority)
            assertEquals(list[i].start, jsonObject[i].start)
            assertEquals(list[i].finish, jsonObject[i].finish)
        }
    }
}