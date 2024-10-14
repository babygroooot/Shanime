plugins {
    alias(libs.plugins.developmentway.android.library.core)
    alias(libs.plugins.developmentway.android.hilt)
}

android {
    namespace = "com.core.domain"
}

dependencies {

    implementation(libs.androidx.paging.common)
    testImplementation(libs.junit.test)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.espresso.core)

    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.common)
}
