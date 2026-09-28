plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.ksp)
}

android {
    namespace = "se.oscarb.quicko.test_app"
    compileSdk = 37
    targetProjectPath = ":app"

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "se.oscarb.quicko.core.testing.HiltTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:testing"))

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android.testing)

    implementation(libs.androidx.core.ktx)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.ui.test.junit4)
}