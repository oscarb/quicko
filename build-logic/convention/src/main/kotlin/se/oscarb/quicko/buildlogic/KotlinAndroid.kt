package se.oscarb.quicko.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.TestExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureKotlinAndroid(
    extension: ApplicationExtension,
) {
    val compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
    val minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()

    extension.compileSdk = compileSdk
    extension.defaultConfig.minSdk = minSdk

    extension.compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    extension.compileOptions.targetCompatibility = JavaVersion.VERSION_17

    extension.buildTypes.findByName("release")?.apply {
        isMinifyEnabled = false
        proguardFiles(
            extension.getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}

internal fun Project.configureKotlinAndroid(
    extension: LibraryExtension,
) {
    val compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
    val minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()

    extension.compileSdk = compileSdk
    extension.defaultConfig.minSdk = minSdk

    extension.compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    extension.compileOptions.targetCompatibility = JavaVersion.VERSION_17

    extension.buildTypes.findByName("release")?.apply {
        isMinifyEnabled = false
        proguardFiles(
            extension.getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}

internal fun Project.configureKotlinAndroid(
    extension: TestExtension,
) {
    val compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
    val minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()

    extension.compileSdk = compileSdk
    extension.defaultConfig.minSdk = minSdk

    extension.compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    extension.compileOptions.targetCompatibility = JavaVersion.VERSION_17

    extension.buildTypes.findByName("release")?.apply {
        isMinifyEnabled = false
        proguardFiles(
            extension.getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
