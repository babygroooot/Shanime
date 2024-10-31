plugins {
    alias(libs.plugins.developmentway.android.application)
    alias(libs.plugins.developmentway.android.application.compose)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.babygroot.shanime"

    defaultConfig {
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
    }

    androidResources {
        generateLocaleConfig = true
    }
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.core.splashscreen)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(projects.core.designsystem)
    implementation(projects.feature.home)
    implementation(projects.feature.discover)
    implementation(projects.feature.seasonal)
    implementation(projects.feature.setting)
    baselineProfile(projects.baselineprofile)
}
