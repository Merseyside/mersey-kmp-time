package com.merseyside.merseyLib.time.units

enum class Month(val index: Int, val days: Int) {

    JANUARY(0, 31), FEBRUARY(1, 28), MARCH(2, 31),
    APRIL(3, 30), MAY(4, 31), JUNE(5, 30),
    JULY(6, 31), AUGUST(7, 31), SEPTEMBER(8, 30),
    OCTOBER(9, 31), NOVEMBER(10, 30), DECEMBER(11, 31);

    companion object {
        fun getByIndex(index: Int): Month {
            return values().find { it.index == index }
                ?: throw IllegalArgumentException("Index must be in 0..6 range")
        }
    }
}