package com.justparokq.homeftp.shared.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.login.presentation.LoginComponent
import com.justparokq.homeftp.shared.ftp.presentation.FtpExplorerComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Login(val component: LoginComponent) : Child()
        class Main(val component: FtpExplorerComponent) : Child()
    }
}
