plugins {
    alias(libs.plugins.quicko.android.library)
    alias(libs.plugins.quicko.android.compose)
}

android {
    namespace = "se.oscarb.quicko.core.ui"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
}
