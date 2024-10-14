plugins {
    alias(libs.plugins.developmentway.android.library.feature)
    alias(libs.plugins.developmentway.android.library.compose)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.feature.setting"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.kotlinx.serialization.json)
}
