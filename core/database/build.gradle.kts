import com.google.devtools.ksp.gradle.KspExtension

plugins {
    alias(libs.plugins.developmentway.android.library.core)
    alias(libs.plugins.developmentway.android.hilt)
    alias(libs.plugins.room.gradlePlugin)
}

android {
    namespace = "com.core.database"

    configure<KspExtension> {
        arg("room.generateKotlin", "true")
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    testImplementation(libs.junit.test)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.espresso.core)

    //Room
    implementation(libs.androidx.room)
    ksp(libs.androidx.room.compiler)

    implementation(projects.core.model)
}
