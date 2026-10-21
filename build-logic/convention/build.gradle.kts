plugins {
    `kotlin-dsl`
}

group = "se.oscarb.quicko.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "quicko.android.application"
            implementationClass = "se.oscarb.quicko.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "quicko.android.library"
            implementationClass = "se.oscarb.quicko.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "quicko.android.compose"
            implementationClass = "se.oscarb.quicko.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "quicko.android.hilt"
            implementationClass = "se.oscarb.quicko.buildlogic.AndroidHiltConventionPlugin"
        }
        register("androidFeature") {
            id = "quicko.android.feature"
            implementationClass = "se.oscarb.quicko.buildlogic.AndroidFeatureConventionPlugin"
        }
        register("androidTest") {
            id = "quicko.android.test"
            implementationClass = "se.oscarb.quicko.buildlogic.AndroidTestConventionPlugin"
        }
    }
}
