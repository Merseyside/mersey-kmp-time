import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.internal.impldep.com.amazonaws.services.kms.model.NotFoundException
import org.gradle.plugin.use.PluginDependency

//inline fun <reified T> Project.findTypedProperty(propertyName: String): T {
//
//    val stringProperty = findProperty(propertyName) as? String
//
//    return stringProperty?.let {
//        when (T::class) {
//            Boolean::class -> stringProperty.toBoolean()
//            Int::class -> stringProperty.toInt()
//            Float::class -> stringProperty
//            else -> it
//        }
//    } as? T ?: throw NotFoundException("Property $propertyName not found")
//}
//
//fun Project.isLocalDependencies(): Boolean =
//    findTypedProperty("build.localDependencies")
//
//fun Project.isLocalAndroidDependencies(): Boolean =
//    findTypedProperty("build.localAndroidDependencies")
//
//fun Project.isLocalKotlinExtLibrary(): Boolean =
//    findTypedProperty("build.localKotlinExtLibrary")
//
//fun Provider<PluginDependency>.id(): String {
//    return get().pluginId
//}