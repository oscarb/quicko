plugins {
    alias(libs.plugins.quicko.android.application)
    alias(libs.plugins.quicko.android.compose)
    alias(libs.plugins.quicko.android.hilt)
    kotlin("plugin.serialization").version(libs.versions.kotlin)
}

android {
    namespace = "se.oscarb.quicko"

    defaultConfig {
        applicationId = "se.oscarb.quicko"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "se.oscarb.quicko.core.testing.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.hilt.navigation.compose)

    // Hilt instrumented tests
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    implementation(projects.core.database)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.feature.quicksettings)
    androidTestImplementation(projects.core.testing)
}
