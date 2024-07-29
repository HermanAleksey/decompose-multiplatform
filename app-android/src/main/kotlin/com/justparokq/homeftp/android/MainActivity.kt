package com.justparokq.homeftp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.justparokq.homeftp.shared.root.DefaultRootComponent
import com.justparokq.homeftp.root.RootContent
import com.justparokq.homeftp.theme.AndroidAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(componentContext = defaultComponentContext())

        setContent {
            AndroidAppTheme {
                RootContent(component = root)
            }
        }
    }
}
