object Modules {
    object Android {
        object MerseyLibs {
            const val archy = ":archy"
            const val adapters = ":adapters"
            const val animators = ":animators"
            const val utils = ":utils"

            const val archyAndroid = ":archy-android"
        }
    }

    object MultiPlatform {

        object MerseyLibs {
            val utils = MultiPlatformModule(":utils-core")
        }

        val time = ":time"

    }
}