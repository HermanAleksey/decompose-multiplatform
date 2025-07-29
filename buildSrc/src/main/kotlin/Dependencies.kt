import org.gradle.api.JavaVersion

object Dependencies {

    object Android {

        val CompileSdk = 34
        val MinSdk = 24
        val TargetSdk = 34

        val VersionCode = 1
        val VersionName = "1.0.0"
    }

    object AndroidX {

        private const val activityComposeVersion = "1.9.0"

        val ActivityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    }

    object Java {

        val Version = JavaVersion.VERSION_11
    }

    object Kotlin {

        private const val version = "2.0.21"

        val JUnit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
    }

    object Decompose {

        private const val essentyVersion = "2.1.0"
        private const val decomposeVersion = "3.1.0"

        val EssentyLifecycle = "com.arkivanov.essenty:lifecycle:$essentyVersion"
        val Decompose = "com.arkivanov.decompose:decompose:$decomposeVersion"
        val DecomposeExtension = "com.arkivanov.decompose:extensions-compose:$decomposeVersion"
    }

    object Coroutines {

        private const val version = "1.8.1"

        val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val Android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        val Test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        val Swing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$version"
    }

    object Ktor {

        private const val httpVersion = "1.1.5"
        private const val version = "3.0.0"

        val Http = "io.ktor:ktor-http:$httpVersion"

        val ClienCio = "io.ktor:ktor-client-cio:$version"
        val ClientCore = "io.ktor:ktor-client-core:$version"
        val ServerCore = "io.ktor:ktor-server-core-jvm:$version"
        val ServerNettyJwm = "io.ktor:ktor-server-netty-jvm:$version"

        val ClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        val ServerContentNegotiation = "io.ktor:ktor-server-content-negotiation:$version"
        val SerializationKotlinX = "io.ktor:ktor-serialization-kotlinx-json:$version"

        val ClientAndroid = "io.ktor:ktor-client-android:$version"
        val ClientDarwin = "io.ktor:ktor-client-darwin:$version"
        val ClientOkHttp = "io.ktor:ktor-client-okhttp:$version"

        val ktorTestJvm = "2.3.13"
        val Tests = "io.ktor:ktor-server-tests-jvm:$ktorTestJvm"
        val Mock = "io.ktor:ktor-client-mock:$ktorTestJvm"

        val ServerAuth = "io.ktor:ktor-server-auth:$version"
        val ServerAuthJwt = "io.ktor:ktor-server-auth-jwt:$version"

        val ClientLogging = "io.ktor:ktor-client-logging:$version"
    }

    object FilePicker {

        // todo up version
        private const val filekitComposeVersion = "0.8.8"

        val Core = "io.github.vinceglb:filekit-core:$filekitComposeVersion"
        val Compose = "io.github.vinceglb:filekit-compose:$filekitComposeVersion"
        val Dialogs = "io.github.vinceglb:filekit-dialogs:$filekitComposeVersion"
        val DialogsCompose = "io.github.vinceglb:filekit-dialogs-compose:$filekitComposeVersion"
        val Coil = "io.github.vinceglb:filekit-coil:$filekitComposeVersion"
    }

    object Logback {

        private const val version = "1.5.6"

        val LogbackClassic = "ch.qos.logback:logback-classic:$version"
    }

    object Room {

        const val version = "2.7.0-rc03"

        val Compiler = "androidx.room:room-compiler:$version"
        val Runtime = "androidx.room:room-runtime:$version"
        val GradlePlugin = "androidx.room:$version"
    }

    object Sqlite {

        const val version = "2.5.0-rc03"

        val Bundled = "androidx.sqlite:sqlite-bundled:$version"
    }

    object Ksp {

        const val version = "2.1.20-1.0.32"
    }

    object Coil {

        private const val coilVersion = "3.0.0"
        val Compose = "io.coil-kt.coil3:coil-compose:$coilVersion"
        val NetworkKtor = "io.coil-kt.coil3:coil-network-ktor3:$coilVersion"
    }

    object Koin {
        private const val version = "3.2.0"

        const val Core = "io.insert-koin:koin-core:${version}"
        const val Test = "io.insert-koin:koin-test:${version}"
        const val Android = "io.insert-koin:koin-android:${version}"
    }

    object Test {

        const val Mockk = "io.mockk:mockk:1.13.8"
    }

    object DataStore {

        private const val version = "1.1.7"

        const val DataStore = "androidx.datastore:datastore:$version"
        const val Core = "androidx.datastore:datastore-core:$version"
        const val Preferences = "androidx.datastore:datastore-preferences:$version"
    }
}