package com.justparokq.homeftp.features.login

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.features.login.composables.LoginCard
import com.justparokq.homeftp.shared.login.presentation.LoginComponent
import com.justparokq.homeftp.shared.login.presentation.PreviewLoginComponent
import com.justparokq.homeftp.theme.AppTheme

@Composable
internal fun LoginContent(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.subscribeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        LoginCard(
            usernameTextValue = state.usernameTextField,
            updateUsername = { component.onUsernameFieldUpdated(it) },
            passwordTextValue = state.passwordTextField,
            updatePassword = { component.onPasswordFieldUpdated(it) },
            isLoading = state.isLoading,
            onLoginButtonClicked = { component.onLoginButtonClick() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(20.dp)
                .defaultMinSize(minWidth = 300.dp, minHeight = 260.dp)
        )
    }
}

@Preview
@Composable
fun LoginContentPreviewLight() {
    AppTheme(darkTheme = true) {
        LoginContent(PreviewLoginComponent)
    }
}

@Preview
@Composable
fun LoginContentPreviewDark() {
    AppTheme(darkTheme = false) {
        LoginContent(PreviewLoginComponent)
    }
}
