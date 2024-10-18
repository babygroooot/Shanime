import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.room.gradlePlugin) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
}
subprojects {
    apply<SpotlessPlugin>()
    extensions.configure<SpotlessExtension> {
        val ktlintVersion = "1.3.1"
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint(ktlintVersion)
                .setEditorConfigPath(
                    rootProject.file(".editorconfig").path
                )
            indentWithSpaces()
            endWithNewline()
            trimTrailingWhitespace()
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint(ktlintVersion)
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }
//    afterEvaluate {
//        // For running spotless everytime the project is being built
//        val spotless = tasks.findByName("spotlessApply")
//        if (spotless != null) {
//            tasks.withType(KotlinCompile::class.java) {
//                finalizedBy(spotless)
//            }
//            tasks.withType(JavaCompile::class.java) {
//                finalizedBy(spotless)
//            }
//        }
//    }
}
true
