
import com.android.build.api.dsl.ApplicationExtension
import com.madskill.mad_skill.AppFlavor
import com.madskill.mad_skill.DefaultConfig
import com.madskill.mad_skill.FlavorConfig
import com.madskill.mad_skill.configureFlavors
import com.madskill.mad_skill.util.configureKotlinAndroid
import com.madskill.mad_skill.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("developmentway.android.hilt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig {
                    applicationId = DefaultConfig.APPLICATION_ID
                    targetSdk = DefaultConfig.TARGET_SDK
                    minSdk = DefaultConfig.MIN_SDK
                    versionCode = DefaultConfig.VERSION_CODE
                    versionName = DefaultConfig.VERSION_NAME
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isDebuggable = false
                        proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                    debug {
                        isMinifyEnabled = false
                        isShrinkResources = false
                    }
                }
                configureFlavors(this) {
                    when(it) {
                        AppFlavor.Dev -> {
                            manifestPlaceholders["appName"] = FlavorConfig.DEV_APP_NAME
                            manifestPlaceholders["appIcon"] = FlavorConfig.ICON_LAUNCHER_DEV
                            manifestPlaceholders["appIconRound"] = FlavorConfig.ICON_LAUNCHER_ROUND_DEV
                        }
                        AppFlavor.Prod -> {
                            manifestPlaceholders["appName"] = FlavorConfig.PROD_APP_NAME
                            manifestPlaceholders["appIcon"] = FlavorConfig.ICON_LAUNCHER_PROD
                            manifestPlaceholders["appIconRound"] = FlavorConfig.ICON_LAUNCHER_ROUND_PROD
                        }
                    }
                }
                buildFeatures {
                    buildConfig = true
                }
                packaging {
                    resources {
                        excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                    }
                }
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