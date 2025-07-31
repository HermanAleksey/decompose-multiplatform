package com.justparokq.homeftp.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface Dimensions {
    val paddingTooSmall: Dp
    val paddingExtraSmall: Dp
    val paddingSmall: Dp
    val paddingNormal: Dp
    val paddingLarge: Dp
    val paddingExtraLarge: Dp
    val normalButtonHeight: Dp
    val minButtonWidth: Dp
}

val normalDimensions: Dimensions = object : Dimensions {
    override val paddingTooSmall: Dp
        get() = 2.dp
    override val paddingExtraSmall: Dp
        get() = 4.dp
    override val paddingSmall: Dp
        get() = 8.dp
    override val paddingNormal: Dp
        get() = 16.dp
    override val paddingLarge: Dp
        get() = 24.dp
    override val paddingExtraLarge: Dp
        get() = 32.dp
    override val normalButtonHeight: Dp
        get() = 56.dp
    override val minButtonWidth: Dp
        get() = 120.dp
}

val LocalAppDimens = staticCompositionLocalOf {
    normalDimensions
}

interface Elevation {
    val none: Dp
    val small: Dp
    val medium: Dp
    val high: Dp
}

val normalElevation = object : Elevation {
    override val none: Dp
        get() = 0.dp
    override val small: Dp
        get() = 8.dp
    override val medium: Dp
        get() = 16.dp
    override val high: Dp
        get() = 22.dp
}

val LocalElevation = staticCompositionLocalOf {
    normalElevation
}

@Composable
fun ProvideDimens(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        values = arrayOf(
            LocalAppDimens provides normalDimensions,
            LocalElevation provides normalElevation
        ), content = content
    )
}