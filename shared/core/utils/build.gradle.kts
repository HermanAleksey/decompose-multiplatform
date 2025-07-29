plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))

                androidMain.dependencies {
                    implementation(Dependencies.AndroidX.ActivityCompose)
                }

                jvmMain.dependencies {
                    implementation(Dependencies.Coroutines.Swing)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.utils"
}