package com.justparokq.homeftp.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.justparokq.homeftp.shared.root.RootComponent
import com.justparokq.homeftp.theme.AppTheme
import platform.UIKit.UIViewController

fun rootViewController(root: RootComponent): UIViewController =
    ComposeUIViewController {
        AppTheme {
            RootContent(component = root, modifier = Modifier.fillMaxSize())
        }
    }
