import com.madskill.mad_skill.util.configureAndroidCompose
import com.madskill.mad_skill.util.libs
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
            extensions.configure<ApplicationExtension> {
                dependencies {
                    add("implementation", libs.findLibrary("androidx.activity.compose").get())
                }
            }
        }
    }

}
