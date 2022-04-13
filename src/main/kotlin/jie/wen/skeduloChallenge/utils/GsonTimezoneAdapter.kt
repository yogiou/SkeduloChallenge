package jie.wen.skeduloChallenge.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class GsonTimezoneAdapter : JsonSerializer<Date?>, JsonDeserializer<Date?> {
    private val dateFormat: DateFormat

    init {
        dateFormat = SimpleDateFormat(TimeUtils.getTimeFormat())
        dateFormat.timeZone = TimeUtils.getTimeZone()
    }

    @Synchronized
    override fun serialize(date: Date?, type: Type?, jsonSerializationContext: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(dateFormat.format(date))
    }

    @Synchronized
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?
    ): Date {
        return try {
            dateFormat.parse(jsonElement.asString)
        } catch (e: Exception) {
            throw JsonParseException(e)
        }
    }
}