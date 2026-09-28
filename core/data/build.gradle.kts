plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
}

android {
    namespace = "se.oscarb.quicko.core.data"
    compileSdk = 37

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "se.oscarb.quicko.core.testing.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)

    implementation(project(":core:model"))
    implementation(project(":core:database"))

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

}