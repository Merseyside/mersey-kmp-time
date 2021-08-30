package com.merseyside.merseyLib.time

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DayOfWeek(val index: Int) {
    @SerialName("0") MONDAY(0),
    @SerialName("1") TUESDAY(1),
    @SerialName("2") WEDNESDAY(2),
    @SerialName("3") THURSDAY(3),
    @SerialName("4") FRIDAY(4),
    @SerialName("5") SATURDAY(5),
    @SerialName("6") SUNDAY(6);

    companion object {
        fun getByIndex(index: Int): DayOfWeek {
            return values().find { it.index == index }
                ?: throw IllegalArgumentException("Index must be in 0..6 range")
        }

        fun getByPlatformIndex(index: Int): DayOfWeek {
            val newIndex = if (index == 1) 6
            else index - 2

            return getByIndex(newIndex)
        }
    }

    fun toPlatformIndex(): Int {
        return if (index == 6) 1
        else index - 2
    }
}