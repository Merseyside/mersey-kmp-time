package com.merseyside.merseyLib.time.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Pattern {
    sealed class Offset : Pattern() {
        object ISO_OFFSET_DATE_TIME : Offset() // '2011-12-03T10:15:30+01:00'
        object ISO_OFFSET_DATE : Offset() //'2011-12-03+01:00'
        object ISO_OFFSET_TIME : Offset() // '10:15+01:00'
        object ISO_OFFSET_FULL_TIME : Offset() // '10:15:30+01:00'
    }

    object ISO_DATE_TIME : Pattern() // '2011-12-03T10:15:30'
    object ISO_INSTANT : Pattern() // '2011-12-03T10:15:30Z'

    object ISO_LOCAL_DATE : Pattern() //'2011-12-03'

    object ISO_LOCAL_TIME : Pattern() // '10:15'
    object ISO_LOCAL_FULL_TIME : Pattern() // '10:15:30'

    open class CUSTOM(val value: String) : Pattern()
    object EMPTY : CUSTOM("")

    override fun toString(): String {
        return if (this is CUSTOM) {
            value
        } else this::class.simpleName ?: "No simple name"
    }

    @OptIn(ExperimentalContracts::class)
    fun isOffsetPattern(): Boolean {
        contract {
            returns(true) implies (this@Pattern is Offset)
        }

        return this is Offset
    }

    fun isTruncatesToMinutes(): Boolean {
        return this is ISO_LOCAL_TIME || this is Offset.ISO_OFFSET_TIME
    }
}
