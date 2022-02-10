package com.merseyside.merseyLib.time.units

import com.merseyside.merseyLib.time.utils.serializers.YearsAsIntSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable(with = YearsAsIntSerializer::class)
value class Years(val value: Int)
