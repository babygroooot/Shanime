import com.madskill.mad_skill.configureFlavors
import com.madskill.mad_skill.util.configureKotlinAndroid
import com.madskill.mad_skill.util.libs
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class CommonModuleGradleConventionPlugin: Plugin<Project> {
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
                buildFeatures {
                    viewBinding = true
                    buildConfig = true
                }
                dependencies {
                    add("implementation", libs.findLibrary("androidx.appcompat").get())
                    add("implementation", libs.findLibrary("androidx.fragment").get())
                }
            }
        }
    }
}