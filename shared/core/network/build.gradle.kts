plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Koin.Core)
                implementation(Dependencies.Coil.NetworkKtor)
                implementation(Dependencies.Ktor.ClientLogging)

                implementation(project(Modules.Shared.Core.DataStore))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(project(Modules.Shared.Core.SettingKey))
                implementation(project(Modules.Model.Login))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.network"
    android.buildFeatures.buildConfig = true
    buildTypes {
        debug { buildConfigField("boolean", "DEBUG", "true") }
        release { buildConfigField("boolean", "DEBUG", "false") }
    }
}