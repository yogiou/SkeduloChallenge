package jie.wen.skeduloChallenge.utils

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import jie.wen.skeduloChallenge.data.Constants.START_TAG
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZoneOffset.UTC
import java.util.*


class TimeUtils {
    companion object {
        private var timeZone: String = ""

        fun getTimeZoneFromJson(json: String) {
            json.takeIf { it.isNotEmpty() }?.let { jsonString ->
                try {
                    val jsonArray: JsonArray = JsonParser.parseString(jsonString).asJsonArray
                    val time = jsonArray[0].asJsonObject.get(START_TAG).asString

                    timeZone = time.takeIf { !it.endsWith("Z") }?.let { time.substring(time.length - 5, time.length - 3) } ?: ""
                } catch (e: Exception) {
                    println("Can't get time zone")
                }
            }
        }

        fun getTimeFormat(): String {
            return when (timeZone.isEmpty()) {
                true -> "yyyy-MM-dd'T'HH:mm:ss'Z'"
                else -> "yyyy-MM-dd'T'HH:mm:ssXXX"
            }
        }

        fun getTimeZone(): TimeZone {
            return timeZone.takeIf { it.isNotEmpty() }?.let {
                val now = Instant.now()
                val targetOffset = ZoneOffset.ofHours(Integer.parseInt(timeZone))
                val zoneNames = ZoneId.getAvailableZoneIds()

                val zoneName = zoneNames.filter { zoneName ->
                    val zoneId = ZoneId.of(zoneName)
                    val rules = zoneId.rules
                    val offset: ZoneOffset = rules.getOffset(now)
                    targetOffset == offset
                }[0]

                TimeZone.getTimeZone(ZoneId.of(zoneName))
            } ?: TimeZone.getTimeZone(UTC)
        }

        fun printTime(date: Date): String {
            val simpleDateFormat = SimpleDateFormat(getTimeFormat())

            simpleDateFormat.timeZone = getTimeZone()

            return simpleDateFormat.format(date)
        }
    }
}