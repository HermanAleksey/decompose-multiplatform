plugins {
    alias(libs.plugins.kotlin.jwm)
    alias(libs.plugins.ktor)
    application
}

group = "com.justparokq"
version = "1.0.0"
application {
    mainClass.set("com.justparokq.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Xms512m")
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.logback)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.ktor.server.auth)

    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}