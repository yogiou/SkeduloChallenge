package jie.wen.skeduloChallenge.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
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
    var start: Date,
    @Expose
    @SerializedName("finish")
    var finish: Date
): Comparable<Performance> {
    override fun compareTo(other: Performance) = when {
        start != other.start -> start.compareTo(other.start)
        priority != other.priority -> other.priority.compareTo(priority)
        else -> (finish.time - start.time).compareTo(other.finish.time - other.start.time)
    }
}
