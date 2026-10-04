plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication3"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication3"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.android.material)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.interpolator)
}

configurations.all {
    resolutionStrategy.force("androidx.core:core-ktx:1.9.0")
}