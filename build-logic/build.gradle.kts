import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.madskill.mad_skill.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.tools.build.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "developmentway.android.library.core"
            implementationClass = "CoreLibraryGradleConventionPlugin"
        }
        register("androidHilt") {
            id = "developmentway.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidNetworkLibrary") {
            id = "developmentway.android.library.network"
            implementationClass = "NetworkGradleConventionPlugin"
        }
        register("androidCommonLibrary") {
            id = "developmentway.android.library.common"
            implementationClass = "CommonModuleGradleConventionPlugin"
        }
        register("androidFeatureLibrary") {
            id = "developmentway.android.library.feature"
            implementationClass = "FeatureLibraryGradleConventionPlugin"
        }
        register("androidApplication") {
            id = "developmentway.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "developmentway.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "developmentway.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidTest") {
            id = "developmentway.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
    }
}