package com.justparokq.homeftp.shared.root.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import com.justparokq.homefpt.shared.core.network.coil.RequestHeaderInterceptor
import com.justparokq.homefpt.shared.core.network.navigation.UnauthNavigator
import com.justparokq.homeftp.shared.features.settings.api.SettingsComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.IInputActionDelegate
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justparokq.homeftp.shared.root.presentation.component.RootComponent.Child
import com.justparokq.homeftp.shared.root.presentation.navigation.Config
import com.justparokq.homeftp.shared.root.presentation.navigation.FeatureNavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, KoinComponent, IInputActionDelegate {

    override val imageRequestInterceptor: RequestHeaderInterceptor by inject()

    private val navigation = StackNavigation<Config>()
    private val featureNavigator: FeatureNavigator =
        FeatureNavigatorImpl(navigation).also { featureNavigator ->
            // loading navigation to the unauth zone
            val navigationModule = module {
                single<UnauthNavigator> {
                    object : UnauthNavigator {
                        override fun navigateToUnauthZone() {
                            featureNavigator.navigateToTheRoot()
                            featureNavigator.replaceCurrentWith(ProjectFeature.LOGIN)
                        }
                    }
                }
            }
            loadKoinModules(navigationModule)
        }

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): Child {
        return when (config) {
            is Config.Login -> Child.Login(loginComponent(childComponentContext))
            is Config.Main -> Child.Main(mainComponent(childComponentContext))
            is Config.Ftp -> Child.Ftp(ftpComponent(childComponentContext))
            is Config.Settings -> Child.Settings(settingsComponent(childComponentContext))
        }
    }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent {
        val comp: LoginComponent by inject { parametersOf(componentContext, featureNavigator) }
        return comp
    }

    private fun mainComponent(componentContext: ComponentContext): MainComponent {
        val comp: MainComponent by inject { parametersOf(componentContext, featureNavigator) }
        return comp
    }

    private fun ftpComponent(componentContext: ComponentContext): FtpExplorerComponent {
        val comp: FtpExplorerComponent by inject {
            parametersOf(
                componentContext,
                featureNavigator
            )
        }
        return comp
    }

    private fun settingsComponent(componentContext: ComponentContext): SettingsComponent {
        val comp: SettingsComponent by inject { parametersOf(componentContext, featureNavigator) }
        return comp
    }

    override fun onBackClicked(): Boolean {
        var result = false
        navigation.pop { isSuccess ->
            result = isSuccess
        }
        return result
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    override fun processBackInput(): Boolean {
        // delegate call to an active screen
        val activeChild = stack.value.active.component2()
        val activeScreenProcessedInput = activeChild.component.processBackInput()
        if (activeScreenProcessedInput) return true

        // default behaviour that is shared for all screens
        return onBackClicked()
    }

    override fun processNextInput(): Boolean {
        // delegate call to an active screen
        val activeChild = stack.value.active.component2()
        activeChild.component.processNextInput()

        return false
    }
}
