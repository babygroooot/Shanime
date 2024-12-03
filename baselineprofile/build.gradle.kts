import com.madskill.mad_skill.configureFlavors

plugins {
    alias(libs.plugins.developmentway.android.test)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.shanime.baselineprofile"

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    targetProjectPath = ":app"

    configureFlavors(this)

    testOptions.managedDevices.devices {
        create<com.android.build.api.dsl.ManagedVirtualDevice>("pixel6Api31") {
            device = "Pixel 6"
            apiLevel = 31
            systemImageSource = "aosp"
        }
    }
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    managedDevices += "pixel6Api31"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.test.ext)
    implementation(libs.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
}

androidComponents {
    onVariants { v ->
        val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { artifactsLoader.load(it)?.applicationId.orEmpty() },
        )
    }
}
