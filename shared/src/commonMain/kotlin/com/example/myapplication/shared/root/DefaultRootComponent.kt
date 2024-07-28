package com.example.myapplication.shared.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.login.data.network.LoginNetworkComponent
import com.example.myapplication.shared.login.presentation.DefaultLoginComponent
import com.example.myapplication.shared.login.presentation.LoginComponent
import com.example.myapplication.shared.main.DefaultMainComponent
import com.example.myapplication.shared.main.MainComponent
import com.example.myapplication.shared.root.RootComponent.Child
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): Child =
        when (config) {
            is Config.Login -> Child.Login(loginComponent(childComponentContext))
            is Config.Main -> Child.Main(mainComponent(childComponentContext))
        }

    private val loginNetworkComponent = LoginNetworkComponent()

    private fun loginComponent(componentContext: ComponentContext): LoginComponent =
        DefaultLoginComponent(
            componentContext = componentContext,
            navigateToMainPage = { navigation.push(Config.Main) },
            loginNetworkComponent = loginNetworkComponent
        )

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            onShowWelcome = { navigation.push(Config.Login) },
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    private sealed interface Config {

        @Serializable
        data object Login : Config

        @Serializable
        data object Main : Config
    }
}
