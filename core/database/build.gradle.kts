plugins {
    alias(libs.plugins.quicko.android.library)
    alias(libs.plugins.quicko.android.hilt)
    alias(libs.plugins.room)
}

android {
    namespace = "se.oscarb.quicko.core.database"

    defaultConfig {
        testInstrumentationRunner = "se.oscarb.quicko.core.testing.HiltTestRunner"
    }

    room {
        schemaDirectory("$projectDir/schemas")
        generateKotlin = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.hilt.navigation.compose)
}
