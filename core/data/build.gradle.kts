plugins {
    alias(libs.plugins.developmentway.android.library.core)
    alias(libs.plugins.developmentway.android.hilt)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.core.data"
}

dependencies {

    //Network
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logger)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.common)

    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(projects.core.common)
}
