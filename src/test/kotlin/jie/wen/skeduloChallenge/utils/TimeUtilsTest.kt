package jie.wen.skeduloChallenge.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.ZoneOffset.UTC
import java.util.TimeZone

internal class TimeUtilsTest {
    @Test
    fun getTimeFormat() {
        val json = "[\n" +
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

        TimeUtils.getTimeZoneFromJson(json)
        assertEquals("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeUtils.getTimeFormat())

        val json2 = "[\n" +
                "{\n" +
                "\"band\":\"Soundgarden\",\n" +
                "\"start\":\"1993-05-25T02:00:00+10:00\",\n" +
                "\"finish\":\"1993-05-25T02:50:00+10:00\"" +
                "},\n" +
                "{\n" +
                "\"band\":\"Pearl Jam\",\n" +
                "\"start\":\"1993-05-25T02:15:00+10:00\",\n" +
                "\"finish\":\"1993-05-25T02:35:00+10:00\"\n" +
                "}\n" +
                "]"

        TimeUtils.getTimeZoneFromJson(json2)
        assertEquals("yyyy-MM-dd'T'HH:mm:ssXXX", TimeUtils.getTimeFormat())
    }

    @Test
    fun getTimeZoneTest() {
        val json = "[\n" +
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

        TimeUtils.getTimeZoneFromJson(json)
        assertEquals(TimeZone.getTimeZone(UTC), TimeUtils.getTimeZone())

        val json2 = "[\n" +
                "{\n" +
                "\"band\":\"Soundgarden\",\n" +
                "\"start\":\"1993-05-25T02:00:00+10:00\",\n" +
                "\"finish\":\"1993-05-25T02:50:00+10:00\"" +
                "},\n" +
                "{\n" +
                "\"band\":\"Pearl Jam\",\n" +
                "\"start\":\"1993-05-25T02:15:00+10:00\",\n" +
                "\"finish\":\"1993-05-25T02:35:00+10:00\"\n" +
                "}\n" +
                "]"

        TimeUtils.getTimeZoneFromJson(json2)
        assertEquals(TimeZone.getTimeZone("Australia/Hobart"), TimeUtils.getTimeZone())
    }
}