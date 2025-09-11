import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType


internal val Project.catalogs: VersionCatalogsExtension
    get() = extensions.getByType()

internal fun VersionCatalogsExtension.getCatalog(name: String) = named(name)

internal fun VersionCatalog.library(name: String) = findLibrary(name).get()

internal fun VersionCatalog.bundle(name: String) = findBundle(name).get()

internal fun VersionCatalog.plugin(name: String) = findPlugin(name).get()

internal fun VersionCatalog.version(name: String) = findVersion(name).get().requiredVersion


internal val Project.androidLibsBuild: VersionCatalog
    get() = catalogs.getCatalog("androidLibs")

internal val Project.commonBuild: VersionCatalog
    get() = catalogs.getCatalog("common")

internal val Project.multiplatformLibsBuild: VersionCatalog
    get() = catalogs.getCatalog("multiplatformLibs")

internal val Project.catalogPluginsBuild: VersionCatalog
    get() = catalogs.getCatalog("catalogPlugins")

internal val Project.iziLibsBuild: VersionCatalog
    get() = catalogs.getCatalog("iziLibs")

internal val Project.composeLibsBuild: VersionCatalog
    get() = catalogs.getCatalog("composeCatalog")