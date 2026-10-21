package se.oscarb.quicko.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<CommonExtension>()
            extension.buildFeatures.compose = true

            val composeBom = libs.findLibrary("androidx-compose-bom").get()
            dependencies {
                add("implementation", platform(composeBom))
                add("androidTestImplementation", platform(composeBom))
            }
        }
    }
}
