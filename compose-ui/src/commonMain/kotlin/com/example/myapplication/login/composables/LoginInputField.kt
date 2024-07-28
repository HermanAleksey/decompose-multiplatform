package com.example.myapplication.login.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp

@Composable
internal fun LoginInputField(
    textFieldValue: String,
    label: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean,
    errorValue: String?,
    isEnabled: Boolean = true,
    focusRequester: FocusRequester? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    testTag: String = "",
    onKeyboardActions: () -> Unit,
) {
    val isPasswordField = remember {
        keyboardType == KeyboardType.Password
    }
    var passwordVisible by rememberSaveable { mutableStateOf(!isPasswordField) }
    var labelFontSize by remember {
        mutableStateOf(18)
    }
    val color = MaterialTheme.colorScheme.scrim
    var labelTextColor by remember {
        mutableStateOf(color)
    }
    TextField(
        enabled = isEnabled,
        value = textFieldValue,
        onValueChange = {
            onValueChanged(it)
        },
        isError = isError,
        textStyle = MaterialTheme.typography.headlineSmall,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = labelFontSize.sp,
                    color = labelTextColor
                ),
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onKeyboardActions()
            },
            onNext = {
                onKeyboardActions()
            }
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(),
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            if (!isPasswordField) return@TextField

            val icon = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible)
                "Hide password"
            else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = icon,
                    tint = Color.White,
                    contentDescription = description
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester())
            .onFocusChanged { focusState ->
                if (textFieldValue.isBlank() && !focusState.isFocused) {
                    labelTextColor = Color.Gray
                    labelFontSize = 22
                } else {
                    labelTextColor = Color.White
                    labelFontSize = 15
                }
            }
            .testTag(testTag)
    )
    if (errorValue != null) {
        Text(
            text = errorValue,
            color = MaterialTheme.colorScheme.error,
        )
    }
}
