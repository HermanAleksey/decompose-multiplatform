package com.example.myapplication.login

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.myapplication.login.composables.LoginCard
import com.example.myapplication.shared.login.presentation.LoginComponent
import com.example.myapplication.shared.login.presentation.PreviewLoginComponent

@Composable
internal fun LoginContent(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.subscribeAsState()
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginCard(
            usernameTextValue = state.usernameTextField,
            updateUsername = { component.onUsernameFieldUpdated(it) },
            passwordTextValue = state.passwordTextField,
            updatePassword = { component.onPasswordFieldUpdated(it) },
            isLoading = state.isLoading,
            onLoginButtonClicked = { component.onLoginButtonClick() },
        )
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    LoginContent(PreviewLoginComponent)
}
