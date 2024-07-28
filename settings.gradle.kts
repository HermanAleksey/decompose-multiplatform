@file:Suppress("UnstableApiUsage")
rootProject.name = "KmpProject"

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

rootProject.name = "decompose-template"
include(":shared")
include(":server")
include(":compose-ui")
include(":app-android")
include(":app-desktop")
