import com.madskill.mad_skill.configureFlavors
import com.madskill.mad_skill.util.configureKotlinAndroid
import com.madskill.mad_skill.util.libs
import com.android.build.gradle.LibraryExtension
import com.madskill.mad_skill.DefaultConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeatureLibraryGradleConventionPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("developmentway.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
                defaultConfig.targetSdk = DefaultConfig.TARGET_SDK
                buildFeatures {
                    buildConfig = true
                }
                resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_").lowercase() + "_"
                dependencies {
                    add("implementation", project(":core:model"))
                    add("implementation", project(":core:domain"))
                    add("implementation", project(":core:common"))
                    add("testImplementation", libs.findLibrary("junit.test").get())
                    add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
                    add("androidTestImplementation", libs.findLibrary("espresso.core").get())
                    add("implementation", libs.findLibrary("material.ui").get())
                    add("implementation", libs.findLibrary("androidx.core.ktx").get())
                    add("implementation", libs.findLibrary("androidx.appcompat").get())
                }
            }
        }
    }

}