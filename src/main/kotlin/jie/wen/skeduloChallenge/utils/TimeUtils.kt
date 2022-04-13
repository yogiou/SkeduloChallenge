package jie.wen.skeduloChallenge.utils

import com.google.gson.JsonArray
import com.google.gson.JsonParser
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
            if (json.isNotEmpty()) {
                try {
                    val jsonArray: JsonArray = JsonParser.parseString(json).asJsonArray
                    val time = jsonArray[0].asJsonObject.get("start").asString

                    timeZone = if (!time.endsWith("Z")) {
                        time.substring(time.length - 5, time.length - 3)
                    } else {
                        ""
                    }
                } catch (e: Exception) {
                    println("Can't get time zone")
                }
            }
        }

        fun getTimeFormat(): String {
            return if (timeZone.isEmpty()) {
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
            } else {
                "yyyy-MM-dd'T'HH:mm:ssXXX"
            }
        }

        fun getTimeZone(): TimeZone {
            if (timeZone.isNotEmpty()) {
                val now = Instant.now()
                val targetOffset = ZoneOffset.ofHours(Integer.parseInt(timeZone))
                val zoneNames = ZoneId.getAvailableZoneIds()
                for (zoneName in zoneNames) {
                    val zoneId = ZoneId.of(zoneName)
                    val rules = zoneId.rules
                    val offset: ZoneOffset = rules.getOffset(now)
                    if (offset == targetOffset) {
                        return TimeZone.getTimeZone(zoneId)
                    }
                }
            }

            return TimeZone.getTimeZone(UTC)
        }

        fun printTime(date: Date): String {
            val simpleDateFormat = SimpleDateFormat(getTimeFormat())

            simpleDateFormat.timeZone = getTimeZone()

            return simpleDateFormat.format(date)
        }
    }
}