package com.justparokq.homefpt.shared.core.network.logging

actual val isDebugBuild: Boolean = getLoggingDebugSettingFromArgs()

private fun getLoggingDebugSettingFromArgs(): Boolean {
    val cmd = System.getProperty("sun.java.command", "")
    return cmd.split(" ")
        .firstOrNull { it.startsWith("-Dlogging.debug=") }
        ?.split("=")
        ?.getOrNull(1)
        ?.toBoolean() ?: false
}

