package com.justparokq.homeftp.shared
actual fun getPlatformName(): String = "Desktop ${System.getProperty("os.name")}"
