pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "quest"
include(":app")
include(":logging-api")
include(":logging-logcat")
include(":config")
include(":time")
include(":analytics-api", ":analytics-impl")
include(":serialization")
include(":share")
include(":theme")
include(":network")
include(":posts-api", ":posts-impl")
include(":lifecycle")


