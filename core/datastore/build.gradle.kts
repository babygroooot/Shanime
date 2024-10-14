plugins {
    alias(libs.plugins.developmentway.android.library.core)
    alias(libs.plugins.developmentway.android.hilt)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.core.datastore"
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents.beforeVariants {
    android.sourceSets.register(it.name) {
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }

    //  Workarounds about this opened issue https://github.com/google/ksp/issues/1603
    afterEvaluate {
        tasks.named("ksp${it.name.replaceFirstChar(Char::uppercaseChar)}Kotlin").configure {
            dependsOn("generate${it.name.replaceFirstChar(Char::uppercaseChar)}Proto")
        }
    }
}

dependencies {

    implementation(libs.datastore.preferences)
    implementation(libs.proto.datastore)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.kotlinx.serialization.json)
}
