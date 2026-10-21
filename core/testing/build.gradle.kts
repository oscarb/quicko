plugins {
    alias(libs.plugins.quicko.android.library)
}

android {
    namespace = "se.oscarb.core.testing"
}

dependencies {
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.espresso.core)
    implementation(libs.hilt.android.testing)
}
