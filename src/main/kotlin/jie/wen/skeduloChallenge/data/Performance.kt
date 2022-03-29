package jie.wen.skeduloChallenge.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.util.*

// json object
data class Performance (
    @Expose
    @SerializedName("band")
    val band: String,
    @SerializedName("priority")
    val priority: Int,
    @Expose
    @SerializedName("start")
    var start: String,
    @Expose
    @SerializedName("finish")
    var finish: String
): Comparable<Performance> {
    override fun compareTo(other: Performance) = when {
        start != other.start -> Date.from(Instant.parse(start)).compareTo(Date.from(Instant.parse(other.start)))
        priority != other.priority -> other.priority.compareTo(priority)
        else -> (Date.from(Instant.parse(finish)).time- Date.from(Instant.parse(start)).time).compareTo(Date.from(Instant.parse(other.finish)).time - Date.from(Instant.parse(other.start)).time)
    }
}
