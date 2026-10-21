plugins {
    alias(libs.plugins.quicko.android.test)
    alias(libs.plugins.ksp)
}

android {
    namespace = "se.oscarb.quicko.test_app"
    targetProjectPath = ":app"

    defaultConfig {
        testInstrumentationRunner = "se.oscarb.quicko.core.testing.HiltTestRunner"
    }
}

dependencies {
    implementation(projects.app)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.testing)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android.testing)

    implementation(libs.androidx.core.ktx)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.ui.test.junit4)
}
