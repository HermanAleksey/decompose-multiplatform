package com.example.myapplication.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.example.myapplication.shared.root.RootComponent
import com.example.myapplication.theme.AppTheme
import platform.UIKit.UIViewController

fun rootViewController(root: RootComponent): UIViewController =
    ComposeUIViewController {
        AppTheme {
            RootContent(component = root, modifier = Modifier.fillMaxSize())
        }
    }
