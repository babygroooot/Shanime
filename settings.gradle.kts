pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}
rootProject.name = "Shanime"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include (":app")
include (":core")
include (":feature")
include (":core:network")
include (":core:domain")
include (":core:model")
include (":core:database")
include (":core:datastore")
include (":core:data")
include (":core:common")
include(":core:designsystem")
include(":feature:home")
include(":feature:discover")
include(":feature:seasonal")
include(":feature:setting")
include(":baselineprofile")
