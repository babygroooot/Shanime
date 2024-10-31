plugins {
    alias(libs.plugins.developmentway.android.library.feature)
    alias(libs.plugins.developmentway.android.library.compose)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.feature.home"
}

dependencies {
    implementation(libs.youtube.player.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(projects.core.designsystem)
    implementation(libs.lottie.compose)
}
