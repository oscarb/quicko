plugins {
    alias(libs.plugins.quicko.android.library)
    alias(libs.plugins.quicko.android.hilt)
}

android {
    namespace = "se.oscarb.quicko.core.data"

    defaultConfig {
        testInstrumentationRunner = "se.oscarb.quicko.core.testing.HiltTestRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)

    implementation(projects.core.model)
    implementation(projects.core.database)
}
