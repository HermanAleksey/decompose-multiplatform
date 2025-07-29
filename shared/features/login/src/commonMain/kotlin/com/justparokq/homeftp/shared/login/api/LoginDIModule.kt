package com.justparokq.homeftp.shared.login.api

import com.justparokq.homeftp.shared.core.setting_store.NetworkStore
import com.justparokq.homeftp.shared.login.network.LoginNetworkComponent
import com.justparokq.homeftp.shared.login.network.LoginRepository
import com.justparokq.homeftp.shared.login.network.LoginRepositoryImpl
import com.justparokq.homeftp.shared.login.presentation.component.DefaultLoginComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.InitHelper
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {
    factory<LoginNetworkComponent> { LoginNetworkComponent(get()) }
    factory<LoginRepository> {
        LoginRepositoryImpl(
            networkSettingsInteractor = get(),
            loginNetworkComponent = get(),
            urlResolver = get()
        )
    }
    factory<LoginComponent> {
        DefaultLoginComponent(
            componentContext = get(),
            loginRepository = get(),
            featureNavigator = get(),
            settingsInteractor = get(),
            initHelper = InitHelper(),
            networkStore = get<NetworkStore>()
        )
    } bind LoginComponent::class
}