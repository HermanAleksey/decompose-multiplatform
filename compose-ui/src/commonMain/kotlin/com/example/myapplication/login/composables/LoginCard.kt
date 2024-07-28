package com.example.myapplication.login.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginCard(
    usernameTextValue: String,
    updateUsername: (String) -> Unit,
    passwordTextValue: String,
    updatePassword: (String) -> Unit,
    isLoading: Boolean,
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 32.dp,
                    vertical = 32.dp
                )
        ) {
            LoginInputField(
                textFieldValue = usernameTextValue,
                label = "Login",
                onValueChanged = { str -> updateUsername(str) },
                isError = false,
                errorValue = null,
                isEnabled = true,
                focusRequester = null,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                testTag = "",
                onKeyboardActions = {},
            )
            Spacer(modifier = Modifier.height(24.dp))
            LoginInputField(
                textFieldValue = passwordTextValue,
                label = "Password",
                onValueChanged = { str -> updatePassword(str) },
                isError = false,
                errorValue = null,
                isEnabled = true,
                focusRequester = null,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                testTag = "",
                onKeyboardActions = {},
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                LoginMainButton(
                    modifier = Modifier,
                    text = "Login",
                    onClick = { onLoginButtonClicked() }
                )
            }
        }
    }
}