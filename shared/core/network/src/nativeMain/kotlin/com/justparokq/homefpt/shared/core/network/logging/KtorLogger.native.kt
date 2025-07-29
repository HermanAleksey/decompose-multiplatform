package com.justparokq.homefpt.shared.core.network.logging

import platform.Foundation.NSProcessInfo

actual val isDebugBuild: Boolean
    get() {
        return (NSProcessInfo.processInfo.environment["LOGGING_DEBUG"] as? String).toBoolean()
    }
