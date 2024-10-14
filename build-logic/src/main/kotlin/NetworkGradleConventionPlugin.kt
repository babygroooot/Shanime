import com.madskill.mad_skill.AppFlavor
import com.madskill.mad_skill.FlavorConfig
import com.madskill.mad_skill.configureFlavors
import com.madskill.mad_skill.util.configureKotlinAndroid
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class NetworkGradleConventionPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this) {
                    when (it) {
                        AppFlavor.Dev -> buildConfigField("String","BASE_URL", FlavorConfig.DEV_BASE_URL)
                        AppFlavor.Prod -> buildConfigField("String","BASE_URL", FlavorConfig.PROD_BASE_URL)
                    }
                }
                buildFeatures {
                    buildConfig = true
                }
            }
        }
    }

}