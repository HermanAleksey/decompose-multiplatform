package com.justparokq.homeftp.shared

actual fun getPlatformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"