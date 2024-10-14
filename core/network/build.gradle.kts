plugins {
    alias(libs.plugins.developmentway.android.library.network)
    alias(libs.plugins.developmentway.android.hilt)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.core.network"
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
}

dependencies {

    testImplementation(libs.junit.test)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.espresso.core)

    //Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logger)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.common)
    implementation(projects.core.datastore)
}
