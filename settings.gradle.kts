@file:Suppress("UnstableApiUsage")

include(":models")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "home-ftp"
include(":models")
include(":shared")
include(":server")
include(":compose-ui")
include(":app-android")
include(":app-desktop")
